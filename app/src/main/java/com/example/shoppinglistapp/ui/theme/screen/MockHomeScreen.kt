package com.example.shoppinglistapp.ui.theme.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppinglistapp.ui.theme.MainViewModel


@Composable
fun MockHomeScreen(
//    paddingValues: PaddingValues,
    viewModel: MainViewModel = hiltViewModel(),
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.isShowCategoryDialog = true
                },
                modifier = Modifier
                    .padding(16.dp)
                    .padding(bottom = 60.dp)
                    .clip(RoundedCornerShape(50)), // 角を丸く
                containerColor = MaterialTheme.colorScheme.onPrimary, // 背景色
                contentColor = Color.White, // アイコンとテキストの色
                elevation = FloatingActionButtonDefaults.elevation(8.dp) // 影を追加
            ) {
                Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "カテゴリ追加",
                        modifier = Modifier.padding(end = 8.dp),
                        tint = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = "カテゴリ追加",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        },
    ) { paddingValues ->
        val dummyCategory = listOf("食料品", "日用品", "ペット", "趣味", "洋服", "本")

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.primary),
        ) {
            Spacer(modifier = Modifier.height(32.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(dummyCategory) {
                    Row {
                        Text(
                            text = it,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
//                    Icon(
//                        Icons.Default.Add,
//                        contentDescription = "add",
//                        modifier = Modifier.clickable {
//                            println("test!!!")
//                        })
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    MockHorizontalScrollableCardList(viewModel)
                }
            }

        }
    }

}

@Composable
fun MockHorizontalScrollableCardList(
    viewModel: MainViewModel = hiltViewModel(),
    ) {
    val dummyItems = listOf("鶏もも肉", "牛肉", "豚肉", "魚", "野菜", "果物")
//    val dummyItems = listOf("鶏もも肉",)
//    var expanded by remember { mutableStateOf(false) }

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(dummyItems) { item ->//todo ダミーデータ差し替え
            MockCard(item)
        }
        item {
            Column(
                modifier = Modifier.fillMaxSize(), // 親コンポーザブルいっぱいに広げる
                verticalArrangement = Arrangement.Center, // 垂直方向に中央寄せ
                horizontalAlignment = Alignment.CenterHorizontally // 水平方向にも中央寄せ (必要であれば)
            ) {
//                AddButton(viewModel)
            }
        }
    }
}

@Composable
fun MockCard(item: String) {
    // メニューが表示されているかを管理する状態
    var expanded by remember { mutableStateOf(false) }
    androidx.compose.material3.Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(250.dp)
            .height(180.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary, // 背景色を薄い灰色に設定
            contentColor = Color.Black // テキスト色を黒に設定
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), // Row を Column いっぱいに広げる
                horizontalArrangement = Arrangement.End // Row 内の要素を右寄せにする
            ) {
                Column {
                    Icon(
                        Icons.Default.Menu, contentDescription = "menu",
                        modifier = Modifier.clickable {
                            expanded = !expanded
                        })
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = { Text("編集") },
                            onClick = { /* Handle edit! */ },
                            leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }
                        )
                        DropdownMenuItem(
                            text = { Text("削除", color = Color.Red) },
                            onClick = { /* Handle settings! */ },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null,
                                    tint = Color.Red // アイコンの色を赤に変更

                                )
                            }
                        )
                    }
                }
            }

            Text(
                text = item,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "メモ",
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = "test,test,test,test,test,test,test,test,test,test,test,test,test,test,test,testtest,test,test,test,test,test,test,testtest,test,test,test,test,test,test,testtest,test,test,test,test,test,test,testtest,test,test,test,test,test,test,testtest,test,test,test,test,test,test,testtest,test,test,test,test,test,test,test",
                fontSize = 8.sp,
                color = Color.Gray
            )

        }

    }
}
@Composable
@Preview
fun Test(){
    MockHomeScreen()
}
