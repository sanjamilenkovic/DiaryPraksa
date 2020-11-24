package com.example.diarypraksa

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class DiaryRepository(private val feelingDao: FeelingDao, private val friendDao: FriendDao) {

        // Room executes all queries on a separate thread.
        // Observed Flow will notify the observer when the data has changed.

        //val allFeelings: Flow<List<Feeling>> = feelingDao.getAllFeelings()


        //val allFriends : Flow<List<Friend>> = friendDao.getAllFriends()

        suspend fun getAllFriends(){
                friendDao.getAllFriends()
        }

        suspend fun getAllFeelings(){
                feelingDao.getAllFeelings()
        }

        // By default Room runs suspend queries off the main thread, therefore, we don't need to
        // implement anything else to ensure we're not doing long running database work
        // off the main thread.

        /*suspend fun getAllFrieds():List<Friend>{
                return friendDao.getAllFriends()
        }*/


        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(newFeeling: Feeling) {
            feelingDao.insert(newFeeling)
        }


        @Suppress("RedundantSuspendModifier")
        @WorkerThread
        suspend fun insert(newFriend: Friend) {
            friendDao.insert(newFriend)
        }
}