package com.example.diarypraksa

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.diarypraksa.MyApplication.Companion.currentApp
import androidx.lifecycle.asLiveData

class FriendViewModel (private val repository: DiaryRepository) : AndroidViewModel(currentApp) {
    val allFriends : LiveData<List<Friend>> = repository.allFriends.asLiveData()

}