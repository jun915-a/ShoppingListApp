package com.example.shoppinglistapp.ui.theme.dialog

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglistapp.ui.theme.MainViewModel

@Composable
fun SearchBarcodeDialog(
    isDialogOpen: Boolean, // ダイアログが表示されるかどうか
    viewModel: MainViewModel = hiltViewModel(),
//    onConfirm: (String) -> Unit // 確定ボタンが押された時に呼ばれる処理
) {
    if (isDialogOpen) {
        // ダイアログの表示
        var isConfirmEnabled by remember { mutableStateOf(false) }
        val context = LocalContext.current

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.isShowSearchBarcodeDialog = false
                    viewModel.detectedCode = ""
                },
                title = {
                    Text("コードが検出されました ${viewModel.detectedCode}")
                },
                text = {
                    Text(viewModel.detectedCode)
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.isShowSearchBarcodeDialog = false
                        if (viewModel.detectedCode.isNotEmpty()) {
                            println("test!!! ${viewModel.detectedCode}")
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://www.google.com/search?q=${viewModel.detectedCode}")
                            )
                            context.startActivity(intent)
                        }
                        viewModel.detectedCode = ""
                    }) {
                        Text("外部ブラウザで検索")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        viewModel.isShowSearchBarcodeDialog = false
                        viewModel.detectedCode = ""
                    }) {
                        Text("戻る")
                    }
                },
            )
        }
    }
}
