package com.example.shoppinglistapp.ui.theme.dialog

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shoppinglistapp.ui.theme.MainViewModel
import com.example.shoppinglistapp.ui.theme.Purple80

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
                    },
                    title = {
                        Text("コードが検出されました")
                    },
                    text = {
                        Text(viewModel.detectedCode)
                    },
                    confirmButton = {
                        TextButton(onClick = {
                            viewModel.isShowSearchBarcodeDialog
                        }) {
                            Text("戻る")
                        }
                    }
                )


            if (viewModel.detectedCode != null) {
                Text("検出されたコード: ${viewModel.detectedCode}")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/search?q=${viewModel.detectedCode}"))
                    context.startActivity(intent)
                }) {
                    Text("外部ブラウザで検索")
                }
            } else {
                Text("コードを検出してください")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun preSearchBarcodeDialog() {
    SearchBarcodeDialog(isDialogOpen = true)
}
