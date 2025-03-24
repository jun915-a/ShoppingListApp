package com.example.shoppinglistapp.ui.theme.db

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update

@Entity(tableName = "Item")
data class Item(
    @PrimaryKey(autoGenerate = true) val id: Int,//autoGenerate = true を指定すると、自動でID生成
    val itemName: String,
    val itemMemo: String
)

@Dao
interface ItemDao{
    @Insert
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM item ORDER BY id ASC")
    suspend fun getAllUsers(): List<Item>
}

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): ItemDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}



//使い方
//fun testDatabase(context: Context) {
//    val db = AppDatabase.getDatabase(context)
//    val userDao = db.userDao()
//
//    runBlocking {
//        val user = User(name = "Taro", age = 25)
//        userDao.insert(user)
//
//        val users = userDao.getAllUsers()
//        users.forEach {
//            println("User: ${it.name}, Age: ${it.age}")
//        }
//    }
//}
