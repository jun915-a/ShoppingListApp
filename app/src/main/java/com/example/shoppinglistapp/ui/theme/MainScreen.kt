package com.example.shoppinglistapp.ui.theme

import MyNavigationBar
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BaseScreen() {
    Scaffold(modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    println("Test!!! onClick")
                },
                modifier = Modifier
                    .padding(16.dp)
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
        bottomBar = {
            MyNavigationBar()
        }) { innerPadding ->
        ParentLayout(innerPadding)
    }
}


@Composable
fun ParentLayout(paddingValues: PaddingValues) {
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
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "add",
                        modifier = Modifier.clickable {
                            println("test!!!")
                        })
                }
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalScrollableCardList()
            }
        }

    }
}

@Composable
fun HorizontalScrollableCardList() {
    val dummyItems = listOf("鶏もも肉", "牛肉", "豚肉", "魚", "野菜", "果物")
//    val dummyItems = listOf("鶏もも肉",)

    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(dummyItems) { item ->//todo ダミーデータ差し替え
            Card(item)
        }
        item {
            Column(
                modifier = Modifier.fillMaxSize(), // 親コンポーザブルいっぱいに広げる
                verticalArrangement = Arrangement.Center, // 垂直方向に中央寄せ
                horizontalAlignment = Alignment.CenterHorizontally // 水平方向にも中央寄せ (必要であれば)
            ) {
                AddButton()
            }
        }
    }
}

@Composable
fun Card(item: String) {
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
                Icon(Icons.Default.Menu, contentDescription = "menu",
                    modifier = Modifier.clickable {
                        println("test!!!!")
                    })
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
fun AddButton() {
    Button(
        onClick = { /* ボタンの処理 */ },
        shape = MaterialTheme.shapes.large, // 丸みを帯びた形状
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple80, // 薄い灰色の背景色
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant // アイコンの色
        ),
        modifier = Modifier
            .size(height = 180.dp, width = 60.dp), // サイズ
        contentPadding = PaddingValues(8.dp)

    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add",

            )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    ShoppingListAppTheme {
        BaseScreen()

    }
}

