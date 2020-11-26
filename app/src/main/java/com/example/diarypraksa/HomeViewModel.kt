package com.example.diarypraksa

import android.content.Intent
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(private val repository: DiaryRepository) : ViewModel() {

    public val feelingLD = MutableLiveData<Feeling>()
    val allFeelingsLD : LiveData<List<Feeling>> = repository.allFeelings.asLiveData()

    fun getFeelingById(id : Int) = viewModelScope.launch {
        feelingLD.postValue(repository.getFeelingById(id));
    }

    fun getFeelingByDate(startDate : Date, endDate : Date) = viewModelScope.launch {
       val temp = (repository.getFeelingByDate(startDate, endDate))
        if (temp != null)
            feelingLD.postValue(temp)
        else
            feelingLD.postValue(Feeling(Calendar.getInstance().time, -1, ""))
    }

    fun insert(feeling: Feeling) = viewModelScope.launch {
        repository.insert(feeling)
    }

    fun update(feeling: Feeling) = viewModelScope.launch {
        repository.updateFeeling(feeling)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun updateMyLiveData(opis: String) {
        val myFeeling = feelingLD.value
        myFeeling?.description = opis
        feelingLD.postValue(myFeeling)
    }

    class HomeViewModelFactory(private val repository: DiaryRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}