package com.example.shoppinglistapp.ui.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.ui.theme.dialog.CategoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
//    private val taskDao: TaskDao
) : ViewModel() {

    //新規カテゴリ追加ダイアログ
    var isShowCategoryDialog by mutableStateOf(false)

    //新規アイテム追加ダイアログ
    var isShowNewItemDialog by mutableStateOf(false)

    //バーコード検索ダイアログ
    var isShowSearchBarcodeDialog by mutableStateOf(false)
    var selectIndex by mutableStateOf(0)


    //    var categoryNameList: MutableList<CategoryItem> = mutableListOf()
    var categoryItemList: MutableList<Pair<String, MutableList<CategoryItem?>>> = mutableListOf()

    var detectedCode by mutableStateOf("")

    //todo Room導入

//    var memo by mutableStateOf("")

    /*
        /*
         * アプリ起動時taskを全件取得
         */
        val tasks = taskDao.loadAllTasks().distinctUntilChanged()

        /*
         * 保存ボタン押下時taskを保存
         */
        fun insertTask(task: Task) {
            viewModelScope.launch {
                taskDao.insertTask(task)
                println("success_test_log ${task.jsonStr} }")
            }
        }

        /*
         * 編集モードから保存した際のアップデート
         */
        fun updateTask(task: Task) {
            viewModelScope.launch {
                taskDao.updateTask(task)
    //            Log.d("success_updateTask", "${task.id} ${task.name} ${task.userId} ${task.password}")
            }
        }

        fun updateIdsAfterDelete(taskId: Int) {
            viewModelScope.launch {
                taskDao.updateIdsAfterDelete(taskId)
            }
        }

        /*
         * TODO　カレンダー下部のアイテム長押しで削除
         */
        fun deleteTask(task: Task) {
            viewModelScope.launch {
                taskDao.deleteTask(task)
    //            Log.d("delete_updateTask", "${task.id} ${task.name} ${task.userId} ${task.password}")
            }
        }

        // オブジェクトをJSON文字列に変換する
        fun toJson(obj: Any): String {
            return Gson().toJson(obj)
        }

        // JSON文字列をオブジェクトに変換する
        inline fun <reified T> fromJson(json: String): T {
            return Gson().fromJson(json, T::class.java)
        }

     */
}
