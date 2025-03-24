package com.example.shoppinglistapp.ui.theme.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglistapp.ui.theme.MainViewModel
import com.example.shoppinglistapp.ui.theme.Purple80

@Composable
fun CategoryDialog(
    viewModel: MainViewModel = hiltViewModel(),
    isDialogOpen: Boolean,
) {
    if (isDialogOpen) {
        var isConfirmEnabled by remember { mutableStateOf(false) }
        var textState by remember { mutableStateOf("") }

        if (textState.isNotEmpty()) {
            isConfirmEnabled = true
        }

        AlertDialog(
            onDismissRequest = {
//                onDismiss()
            },
            title = {
                Text(text = "カテゴリを追加")
            },
            text = {
                Column {
                    Text(text = "追加したいカテゴリ名を入力してください")
                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = textState,
                        onValueChange = { textState = it }, // 入力されたテキストを保持
                        label = { Text("カテゴリ", color = Color.Black) },
                        singleLine = true, // 1行に制限
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(cursorColor = Color.Black)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.categoryItemList.add(Pair(textState, mutableListOf()))
                        viewModel.isShowCategoryDialog = false
                    },
                    enabled = isConfirmEnabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isConfirmEnabled) Purple80 else Color.Gray,
                        contentColor = Color.Black
                    )
                ) {
                    Text("追加")
                }

            },
            dismissButton = {
                Button(
                    onClick = {
                        viewModel.isShowCategoryDialog = false
                    },
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Purple80,
                        contentColor = Color.Black
                    )
                ) {
                    Text("キャンセル")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDialog() {
//    CategoryDialog(true)
}
