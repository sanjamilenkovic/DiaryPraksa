package com.example.diarypraksa

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.Console
import java.util.*

class HomeViewModel(private val repository: DiaryRepository) : ViewModel() {



    //Feeling


    //zar ne bi trebalo da MutableLiveData bude private, a da imam public LiveData kojem mogu da pristupim iz fragmenta
    public val feelingLD = MutableLiveData<Feeling>()

    val allFeelingsLD: LiveData<List<Feeling>> = repository.allFeelings.asLiveData()

    fun getFeelingById(id: Int) = viewModelScope.launch {
        feelingLD.postValue(repository.getFeelingById(id));
    }

    fun getFeelingByDate(startDate: Date, endDate: Date) = viewModelScope.launch {
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

    fun deleteAllFeelings() = viewModelScope.launch {
        repository.deleteAllFeelings()
    }

    fun updateMyLiveData(opis: String) {
        val myFeeling = feelingLD.value
        myFeeling?.description = opis
        feelingLD.postValue(myFeeling)
    }

    fun updateMyLiveData(index: Int) {
        val myFeeling = feelingLD.value
        myFeeling?.sticker_id = index
        feelingLD.postValue(myFeeling)
    }



    //Friend



    val probaPrijatelji: LiveData<List<Friend>> = repository.allFriends.asLiveData()

    public val friendLD = MutableLiveData<Friend>()
    public val bestFriendLD = MutableLiveData<Int>()
    public val friendsLD = MutableLiveData<List<Friend>>()

    fun getAllFriends() = viewModelScope.launch {
        val allFriends = repository.getAllFriends()
        friendsLD.postValue(allFriends.asLiveData().value)
    }

    fun getFriendByDate(startDate: Date, endDate: Date) = viewModelScope.launch {
        val temp = (repository.getFriendByDate(startDate, endDate))
        friendLD.postValue(temp)
    }

    fun getFriendById(id: Int): Job = viewModelScope.launch {
        val temp =  (repository.getFriendById(id))
        friendLD.postValue(temp)
    }

    fun insert(friend: Friend) = viewModelScope.launch {
        repository.insertFriend(friend)
    }

    fun insertBestFriend(friend: Friend) = viewModelScope.launch {
        val idNewBestFriend= repository.insertFriend(friend)
        bestFriendLD.postValue(idNewBestFriend);
    }

    fun updateBestFriend(friend: Friend) = viewModelScope.launch {
        val idNewBestFriend= repository.updateFriend(friend)
        bestFriendLD.postValue(idNewBestFriend);
    }

    fun update(friend: Friend) = viewModelScope.launch {
        repository.updateFriend(friend)
    }

    fun updateById(friend: Friend) = viewModelScope.launch {
        repository.updateFriendById(friend)
    }

//    fun updateMyLiveData(newFriend: Friend) {
//
//        if (friendLD.value != null) {
//            val currentFriend = friendLD.value
//            currentFriend?.name = newFriend.name
//            currentFriend?.lastName = newFriend.lastName
//            currentFriend?.image = newFriend.image
//            currentFriend?.email = newFriend.email
//            currentFriend?.number = newFriend.number
//            currentFriend?.notes = newFriend.notes
//            currentFriend?.name = newFriend.name
//            friendLD.postValue(currentFriend)
//        }
//        else {
//            val temp = Friend(
//                newFriend.image,
//                newFriend.name,
//                newFriend.lastName,
//                newFriend.date,
//                newFriend.email,
//                newFriend.notes,
//                newFriend.number
//            )
//            friendLD.postValue(temp)
//        }
//    }

    fun deleteAllFriends() = viewModelScope.launch {
        repository.deleteAllFriends()
    }




    class HomeViewModelFactory(private val repository: DiaryRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}