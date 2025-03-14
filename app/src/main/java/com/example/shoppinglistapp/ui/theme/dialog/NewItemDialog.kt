package com.example.shoppinglistapp.ui.theme.dialog

import android.content.Context
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglistapp.ui.theme.MainViewModel
import com.example.shoppinglistapp.ui.theme.Purple80
import kotlin.coroutines.coroutineContext


@Composable
fun NewItemDialog(
    viewModel: MainViewModel = hiltViewModel(),
    isNewDialogOpen: Boolean,
    isEditDialogOpen: Boolean,
    context: Context
//    onConfirm: (String) -> Unit // 確定ボタンが押された時に呼ばれる処理
) {
    if (isNewDialogOpen || isEditDialogOpen) {
        // ダイアログの表示
        var isConfirmEnabled by remember { mutableStateOf(false) }

        var itemState by remember { mutableStateOf<CategoryItem>(CategoryItem("", "")) }


        if (itemState.itemName.isNotEmpty()) {
            isConfirmEnabled = true
        }
        AlertDialog(
            onDismissRequest = {
//                onDismiss()
            },
            title = {
                Text(text = "アイテムの追加") // ダイアログのタイトル
            },
            text = {
                // EditText（TextField）を表示

                Column {
                    Text(text = "編集したい内容を入力してください") // 必要なら説明文
                    Spacer(modifier = Modifier.height(8.dp)) // 少しスペースを空ける

                    TextField(
                        value = itemState.itemName,
                        onValueChange = { newValue ->
                            itemState = itemState.copy(itemName = newValue)
                        },
                        label = { Text("アイテム名", color = Color.Black) },
                        singleLine = true, // 1行に制限
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.colors(cursorColor = Color.Black)

                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    itemState.memo?.let {
                        TextField(
                            value = it,
                            onValueChange = { newValue ->
                                itemState = itemState.copy(memo = newValue)
                            }, label = { Text("メモ", color = Color.Black) },
                            singleLine = false,
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(cursorColor = Color.Black)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (isNewDialogOpen) {
                            viewModel.categoryItemList[viewModel.selectIndex].second.add(itemState)
                            viewModel.isShowNewItemDialog = false
                        }
                        if (isEditDialogOpen) {
                            viewModel.categoryItemList.forEach { pair ->
                                val list = pair.second
                                val index = list.indexOfFirst { it == viewModel.editItem }
                                if (index != -1) {
                                    list[index] = CategoryItem(itemState.itemName, itemState.memo)
                                    viewModel.isShowEditItemDialog = false
                                    return@forEach
                                }
                            }
                        }
                        viewModel.saveCategoryItemList(
                            context = context,
                            viewModel.categoryItemList
                        )
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
                        viewModel.isShowNewItemDialog = false
                        viewModel.isShowEditItemDialog = false

                    }, enabled = true,
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

