package com.example.diarypraksa

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.*

class DiaryRepository(private val feelingDao: FeelingDao, private val friendDao: FriendDao) {


    //Feeling


    val allFeelings: Flow<List<Feeling>> = feelingDao.getAllFeelings()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(newFeeling: Feeling) {
        feelingDao.insert(newFeeling)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllFeelings() {
        feelingDao.deleteAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getFeelingById(id: Int): Feeling {
        return feelingDao.getFeelingById(id);
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getFeelingByDate(startDate: Date, endDate: Date): Feeling {
        return feelingDao.getFeelingByDate(startDate, endDate);
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun updateFeeling(feeling: Feeling) {
        if (feeling.id != 0)
            feelingDao.updateFeeling(feeling.description, feeling.sticker_id, feeling.id)
        else
            feeling.id = feelingDao.insert(feeling).toInt()
    }


    //Friend


    val allFriends: Flow<List<Friend>> = friendDao.getAllFriends()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllFriends(): Flow<List<Friend>> {
        return friendDao.getAllFriends()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertFriend(newFriend: Friend) : Int{
        var idFriend: Int = friendDao.insert(newFriend).toInt()
        return idFriend
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getFriendByDate(startOfDay: Date, endOfDay: Date): Friend {
        return friendDao.getFriendByDate(startOfDay, endOfDay);
    }


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllFriends() {
        return friendDao.deleteAll();
    }


    suspend fun updateFriend(friend: Friend)  : Int{
        if (friend.id != 0)
            friendDao.updateFriend(
                friend.id,
                friend.image,
                friend.name,
                friend.lastName,
                friend.email,
                friend.notes,
                friend.number
            )
        else
            friend.id = friendDao.insert(friend).toInt()

        return friend.id
    }

    suspend fun getFriendById(id: Int): Friend {
        return friendDao.getFriendById(id)
    }

    suspend fun updateFriendById(friend: Friend) {
        if (friend.id != 0)
            friendDao.updateFriendById(
                friend.id,
                friend.image,
                friend.name,
                friend.lastName,
                friend.email,
                friend.notes,
                friend.number
            )
        else
            friend.id = friendDao.insert(friend).toInt()
    }

}