package com.example.shoppinglistapp.ui.theme.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglistapp.ui.theme.MainViewModel


@Composable
fun NewItemDialog(
    viewModel: MainViewModel = hiltViewModel(),
    isDialogOpen: Boolean, // ダイアログが表示されるかどうか
//    onConfirm: (String) -> Unit // 確定ボタンが押された時に呼ばれる処理
) {
    if (isDialogOpen) {
        // ダイアログの表示
        AlertDialog(
            onDismissRequest = {
//                onDismiss()
            }, // ダイアログ外をタップしたときに閉じる
            title = {
                Text(text = "アイテムの追加") // ダイアログのタイトル
            },
            text = {
                // EditText（TextField）を表示
                var textState by remember { mutableStateOf("") } // テキストを保持する状態

                Column {
                    Text(text = "編集したい内容を入力してください") // 必要なら説明文
                    Spacer(modifier = Modifier.height(8.dp)) // 少しスペースを空ける

                    TextField(
                        value = textState,
                        onValueChange = { textState = it }, // 入力されたテキストを保持
                        label = { Text("アイテム名") }, // ユーザーに何を入力すべきか指示
                        singleLine = true, // 1行に制限
                        modifier = Modifier.fillMaxWidth() // 横幅いっぱいに広げる
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // 少しスペースを空ける

                    TextField(
                        value = textState,
                        onValueChange = { textState = it }, // 入力されたテキストを保持
                        label = { Text("メモ") }, // ユーザーに何を入力すべきか指示
                        singleLine = false,
                        modifier = Modifier.fillMaxWidth() // 横幅いっぱいに広げる
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
//                    onConfirm("textState") // 確定ボタンが押された時に入力内容を返す
                }) {
                    Text("確認")
                }
            },
            dismissButton = {
                Button(onClick = {
                    viewModel.isShowNewItemDialog = false
                }) {
                    Text("キャンセル")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNewDialog() {
    NewItemDialog(isDialogOpen = true)
}
