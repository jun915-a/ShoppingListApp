package com.example.shoppinglistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglistapp.ui.theme.db.Item
import com.example.shoppinglistapp.ui.theme.db.ItemDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val itemDao: ItemDao) : ViewModel() {

//    fun insertUser(item: Item) {
//        viewModelScope.launch {
//            itemDao.insert(item)
//        }
//    }
//
//    suspend fun getAllUsers(): List<Item> {
//        return itemDao.getAllUsers()
//    }
}
//private val userViewModel: UserViewModel by viewModels()

//val user = User(name = "Taro", age = 25)
//userViewModel.insertUser(user)
