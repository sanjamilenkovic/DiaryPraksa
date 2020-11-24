package com.example.diarypraksa

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CalendarViewModel (private val repository: DiaryRepository): ViewModel(){


    fun insert(friend: Friend) = viewModelScope.launch {
        repository.insert(friend)
    }



    fun insert(feeling: Feeling) = viewModelScope.launch {
        repository.insert(feeling)
    }


}

class CalendarViewModelFactory(private val repository: DiaryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}







