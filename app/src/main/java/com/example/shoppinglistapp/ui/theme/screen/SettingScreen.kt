package com.example.shoppinglistapp.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglistapp.ui.theme.MainViewModel
import com.example.shoppinglistapp.ui.theme.Purple80
import com.example.shoppinglistapp.ui.theme.ShoppingListAppTheme
import kotlinx.coroutines.delay

@Composable
fun SettingsScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel
//    onDeleteData: () -> Unit, onShareToLine: () -> Unit
) {
    DeleteDialog(viewModel)
    var showDialog by remember { mutableStateOf(false) }

    // 画面遷移処理
    LaunchedEffect(Unit) {
        showDialog = true // モーダル表示
        // 時間のかかる処理
        delay(1000)
        showDialog = false // モーダル非表示
    }
    if (showDialog) {
        Dialog(onDismissRequest = { /* ダイアログ外クリックで閉じないようにする場合は空 */ }) {
            CircularProgressIndicator()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "設定", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // データの全削除ボタン（赤色・警告付き）
        Button(
            onClick = {
                viewModel.isShowDeleteDialog = true
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "⚠ データの全削除", color = Color.Black)
        }

        // TODO LINEで友達と共有ボタン
//        Button(
//            onClick = { },
//            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary), // LINEの緑色
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text(text = "LINEで友達と共有", color = Color.Black)
//        }
    }

}

@Composable
fun DeleteDialog(
    viewModel: MainViewModel = hiltViewModel(),
) {
    if (viewModel.isShowDeleteDialog) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlertDialog(
                onDismissRequest = {
                    viewModel.isShowDeleteDialog = false
                },
                title = {
                    Text("登録アイテムの削除")
                },
                text = {
                    Text("登録したアイテムを全て削除しますか？")
                },
                confirmButton = {
                    Button(
                        onClick = {
                            viewModel.isShowDeleteDialog = false
                            viewModel.categoryItemList = mutableListOf()
                        },
                        enabled = true,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple80,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("削除する")
                    }
                },
                dismissButton = {
                    Button(
                        onClick = {
                            viewModel.isShowDeleteDialog = false
                        }, enabled = true,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Purple80,
                            contentColor = Color.Black
                        )
                    ) {
                        Text("いいえ")
                    }
                },
            )
        }
    }
}

@Preview
@Composable
fun Preview() {
    ShoppingListAppTheme {
//        SettingsScreen()
    }
}

