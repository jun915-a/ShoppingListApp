package com.example.shoppinglistapp.ui.theme

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.shoppinglistapp.ui.theme.dialog.CategoryItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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

    var isShowEditItemDialog by mutableStateOf(false)
    var editItem: CategoryItem? = null

    //バーコード検索ダイアログ
    var isShowSearchBarcodeDialog by mutableStateOf(false)
    var selectIndex by mutableStateOf(0)

    //削除確認ダイアログ
    var isShowDeleteDialog by mutableStateOf(false)


    //    var categoryNameList: MutableList<CategoryItem> = mutableListOf()
    var categoryItemList: MutableList<Pair<String, MutableList<CategoryItem?>>> = mutableListOf()

    var detectedCode by mutableStateOf("")


    fun saveCategoryItemList(context: Context, categoryItemList: MutableList<Pair<String, MutableList<CategoryItem?>>>) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gson = Gson()
        val json = gson.toJson(categoryItemList) // リストをJSON文字列に変換

        editor.putString("categoryItemList", json) // JSON文字列を保存
        editor.apply()
    }

    fun getCategoryItemList(context: Context): MutableList<Pair<String, MutableList<CategoryItem?>>> {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val json = sharedPreferences.getString("categoryItemList", null) // JSON文字列を取得

        if (json != null) {
            val gson = Gson()
            val type = object : TypeToken<MutableList<Pair<String, MutableList<CategoryItem?>>>>() {}.type
            return gson.fromJson(json, type) // JSON文字列をリストに変換
        }

        return mutableListOf() // データが存在しない場合は空のリストを返す
    }

    fun deleteCategoryItemList(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear() // すべてのデータを削除
        editor.apply()
    }

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
