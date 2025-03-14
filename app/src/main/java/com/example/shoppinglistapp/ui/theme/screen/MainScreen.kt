package com.example.shoppinglistapp.ui.theme.screen

import MyNavigationBar
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shoppinglistapp.ui.theme.MainViewModel
import com.example.shoppinglistapp.ui.theme.Purple80
import com.example.shoppinglistapp.ui.theme.dialog.CategoryDialog
import com.example.shoppinglistapp.ui.theme.dialog.CategoryItem
import com.example.shoppinglistapp.ui.theme.dialog.NewItemDialog
import com.example.shoppinglistapp.ui.theme.dialog.SearchBarcodeDialog

@Composable
fun BaseScreen(
    viewModel: MainViewModel = hiltViewModel(),
    context: Context
) {
    CategoryDialog(isDialogOpen = viewModel.isShowCategoryDialog)
    NewItemDialog(
        isNewDialogOpen = viewModel.isShowNewItemDialog,
        isEditDialogOpen = viewModel.isShowEditItemDialog,
        context = context
    )
    SearchBarcodeDialog(viewModel.isShowSearchBarcodeDialog)
    val navController = rememberNavController()

    Scaffold(modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MyNavigationBar(navController)
        }) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
        ) {
            composable("home") {
                HomeScreen(innerPadding, viewModel)
//                MockHomeScreen(innerPadding, viewModel) //デモデータ
            }
            composable("barcodeScan") { CameraScreen(viewModel) }
            composable("setting") { SettingsScreen(paddingValues = innerPadding,viewModel, context = context) }
        }

    }
}

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val listState = rememberLazyListState()

    val isScrolledToEnd by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == viewModel.categoryItemList.lastIndex
        }
    }
    Scaffold(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        floatingActionButton = {
            AnimatedVisibility(
                visible = isScrolledToEnd || viewModel.categoryItemList.size < 3,
                enter = fadeIn(),
                exit = fadeOut(),
            ) {
                FloatingActionButton(
                    onClick = {
                        viewModel.isShowCategoryDialog = true
                    },
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(50)),
                    containerColor = MaterialTheme.colorScheme.onPrimary, // 背景色
                    contentColor = Color.White, // アイコンとテキストの色
                    elevation = FloatingActionButtonDefaults.elevation(8.dp) // 影
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
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.primary)
                .padding(innerPadding),
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                state = listState
            ) {
                itemsIndexed(viewModel.categoryItemList) { index, categoryItemList ->
                    //TODO ここのIndexを使ってアイテムの追加時にIndex+1の要素にaddする処理を追加する
                    Text(
                        text = categoryItemList.first,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    HorizontalScrollableCardList(viewModel, categoryItemList, index)
                }
            }
        }
    }
}


@Composable
fun HorizontalScrollableCardList(
    viewModel: MainViewModel = hiltViewModel(),
    categoryList: Pair<String, MutableList<CategoryItem?>>,
    index: Int
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(categoryList.second) { item ->
            Card(item, viewModel)
        }
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddButton(viewModel, index)
            }

        }
    }
}

@Composable
fun Card(categoryItem: CategoryItem?, viewModel: MainViewModel) {
    var expanded by remember { mutableStateOf(false) }
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .width(250.dp)
            .height(180.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondary,
            contentColor = Color.Black
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End // Row 内の要素を右寄せ
            ) {
                Column {
                    Icon(Icons.Default.Menu, contentDescription = "menu",
                        modifier = Modifier.clickable {
                            expanded = !expanded
                        })
                    DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                        DropdownMenuItem(
                            text = { Text("編集") },
                            onClick = {
                                expanded = false
                                viewModel.categoryItemList.forEach { pair ->
                                    val list = pair.second
                                    val index = list.indexOfFirst { it == categoryItem }
                                    if (index != -1) {
                                        viewModel.editItem = list[index]
                                        viewModel.isShowEditItemDialog = true

                                        return@forEach
                                    }
                                }
                            },
                            leadingIcon = { Icon(Icons.Outlined.Edit, contentDescription = null) }
                        )
                        DropdownMenuItem(
                            text = { Text("削除", color = Color.Red) },
                            onClick = {
                                expanded = false
                                viewModel.categoryItemList.forEach { pair ->
                                    val list = pair.second
                                    val index = list.indexOfFirst { it == categoryItem }
                                    if (index != -1) {
                                        list.removeAt(index)
                                        viewModel.isShowNewItemDialog = true
                                        viewModel.isShowNewItemDialog = false

                                        return@forEach
                                    }
                                }
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Outlined.Delete,
                                    contentDescription = null,
                                    tint = Color.Red
                                )
                            }
                        )
                    }
                }
            }

            Text(
                text = categoryItem?.itemName!!,
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
                text = categoryItem?.memo ?: "",
                fontSize = 8.sp,
                color = Color.Gray
            )

        }

    }
}

@Composable
fun AddButton(
    viewModel: MainViewModel = hiltViewModel(),
    index: Int
) {
    Button(
        onClick = {
            viewModel.selectIndex = index
            viewModel.isShowNewItemDialog = true
        },
        shape = MaterialTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = Purple80, //背景
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant //アイコン色
        ),
        modifier = Modifier
            .size(height = 180.dp, width = 60.dp),
        contentPadding = PaddingValues(8.dp)

    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Add",
        )
    }
}
