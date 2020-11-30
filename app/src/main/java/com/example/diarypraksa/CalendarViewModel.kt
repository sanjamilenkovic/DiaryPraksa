package com.example.diarypraksa

import adapters.CalendarAdapter
import androidx.appcompat.view.menu.MenuView
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.util.*

class CalendarViewModel (private val repository: DiaryRepository): ViewModel(){
private val _readFeeling= MutableLiveData<Feeling>()
public val readFeeling: LiveData<Feeling>
    get() {
        return _readFeeling
    }
    private val _readFriend= MutableLiveData<Friend>()
    public val readFriend: LiveData<Friend>

    get() {
            return _readFriend
        }
    private val _readFriends= MutableLiveData<List<Friend>>()

    public val readFriends: LiveData<List<Friend>>

        get() {
            return _readFriends
        }


    fun insert(feeling: Feeling) = viewModelScope.launch {
        repository.insert(feeling)
    }

    fun insert(friend: Friend) = viewModelScope.launch {
        repository.insert(friend)
    }

    fun insertTest2() = viewModelScope.launch {
        for (i in 1 until 15){
        var cal=Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR,-i)
            repository.insert(Friend("blablab","stefan$i","stojanovic",cal.time,"stefa@gmail.com","fsafsafsafa","fasfasfsfasfas"))

    }
        }



    fun insertTest() = viewModelScope.launch {

        for (i in 1 until 30) {
            var cal = Calendar.getInstance()
            cal.add(Calendar.DAY_OF_YEAR,-i)
            repository.insert(Feeling(cal.time,1,"danas se osecam ovako"))

        }
    }

    fun readFeelingTest() = viewModelScope.launch {
        _readFeeling.postValue(repository.getOneFeeling())

    }

    fun readFeelingByDate(date: Date) = viewModelScope.launch {
        _readFeeling.postValue(repository.getFeelingByDate(date))

    }


    fun readFriendByDate(date: Date) = viewModelScope.launch {
        _readFriend.postValue(repository.getFriendByDate(date))

    }

    fun readFriendsByDate(date: Date) = viewModelScope.launch {
        _readFriends.postValue(repository.getFriendsByDate(date).asLiveData().value)

    }








}

public class CalendarViewModelFactory(private val repository: DiaryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CalendarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CalendarViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }


}







