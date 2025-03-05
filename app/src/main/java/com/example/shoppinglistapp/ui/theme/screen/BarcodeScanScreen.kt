package com.example.shoppinglistapp.ui.theme.screen

import android.Manifest
import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.shoppinglistapp.ui.theme.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestCameraPermission(
    onPermissionGranted: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    LaunchedEffect(Unit) {
        if (!cameraPermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
        } else {
            onPermissionGranted()
        }
    }

    if (cameraPermissionState.status.isGranted) {
        onPermissionGranted()
    } else {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("カメラの権限が必要です")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
                Text("権限を許可")
            }
        }
    }
}

@Composable
fun CameraScreen(viewModel: MainViewModel) {
    var hasPermission by remember { mutableStateOf(false) }
    var detectedBarcodes by remember { mutableStateOf<List<Barcode>>(emptyList()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    RequestCameraPermission {
        hasPermission = true
    }

    if (hasPermission) {
        CameraPreview(viewModel, onBarcodeDetected = { barcodes ->
            detectedBarcodes = barcodes
            errorMessage = null
        }, onError = { exception ->
            errorMessage = "Error: ${exception.message}"
        })

        if (detectedBarcodes.isNotEmpty()) {
            for (barcode in detectedBarcodes) {
                viewModel.detectedCode = barcode.rawValue.toString()
            }
            detectedBarcodes = emptyList()
            if (viewModel.detectedCode.isNotEmpty()) {
                viewModel.isShowSearchBarcodeDialog = true
            }
        }

        if (errorMessage != null) {
            Text(errorMessage!!)
        }
    }
}

@Composable
fun CameraPreview(
    viewModel: MainViewModel,
    onBarcodeDetected: (List<Barcode>) -> Unit,
    onError: (Exception) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val executor = ContextCompat.getMainExecutor(context)

    AndroidView(factory = { ctx ->
        val previewView = PreviewView(ctx).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
        }

        val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)

        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().apply {
                setSurfaceProvider(previewView.surfaceProvider)
            }

            val imageCapture = ImageCapture.Builder().build()

            // ImageAnalysis の設定
            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build()
                .also { analysisUseCase ->
                    analysisUseCase.setAnalyzer(
                        Executors.newSingleThreadExecutor()
                    ) { imageProxy ->
                        val image = imageProxy.image
                        if (image != null) {
                            val inputImage = InputImage.fromMediaImage(
                                image, imageProxy.imageInfo.rotationDegrees
                            )
                            val scanner = BarcodeScanning.getClient()
                            scanner.process(inputImage).addOnSuccessListener { barcodes ->
                                if (barcodes.isNotEmpty()) {
                                    onBarcodeDetected(barcodes)
                                }
                            }.addOnFailureListener { e ->
                                Log.e("BarcodeScanner", "Barcode scanning failed", e)
                                onError(e)
                            }.addOnCompleteListener {
                                imageProxy.close()
                            }
                        } else {
                            imageProxy.close()
                        }
                    }
                }

            val cameraSelector =
                CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture,
                    imageAnalysis // ImageAnalysis を追加
                )
            } catch (exc: Exception) {
                onError(exc)
            }
        }, executor)

        previewView
    })
}
