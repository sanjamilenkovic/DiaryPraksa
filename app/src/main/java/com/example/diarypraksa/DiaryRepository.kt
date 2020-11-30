package com.example.diarypraksa

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import java.util.*

class DiaryRepository(private val feelingDao: FeelingDao, private val friendDao: FriendDao) {

        // Room executes all queries on a separate thread.
        // Observed Flow will notify the observer when the data has changed.

        //val allFeelings: Flow<List<Feeling>> = feelingDao.getAllFeelings()


        //val allFriends : Flow<List<Friend>> = friendDao.getAllFriends()


        //var currentDate: Date = Calendar.getInstance().time


        suspend fun getAllFriends(){
                friendDao.getAllFriends()
        }



        suspend fun getAllFeelings(){
                feelingDao.getAllFeelings()
        }

        suspend fun getOneFeeling(): Feeling {
                return feelingDao.getFeelingById(2)
        }

        suspend fun getFeelingByDate(date: Date):Feeling {

                val c = Calendar.getInstance()
                c.time=date
                c.set(Calendar.HOUR,0)
                c.set(Calendar.MINUTE,0)
                c.set(Calendar.SECOND,1)
                val startDate: Date = c.time
                val a = Calendar.getInstance()
                a.time=date
                a.set(Calendar.HOUR,23)
                a.set(Calendar.MINUTE,59)
                a.set(Calendar.SECOND,59)
                val endDate: Date = a.time

                return feelingDao.getFeelingByDate(startDate,endDate)
        }

        suspend fun getFriendByDate(date: Date):Friend {

                val c = Calendar.getInstance()
                c.time=date
                c.set(Calendar.HOUR,0)
                c.set(Calendar.MINUTE,0)
                c.set(Calendar.SECOND,1)
                val startDate: Date = c.time
                val a = Calendar.getInstance()
                a.time=date
                a.set(Calendar.HOUR,23)
                a.set(Calendar.MINUTE,59)
                a.set(Calendar.SECOND,59)
                val endDate: Date = a.time

                return friendDao.getFriendByDate(startDate,endDate)
        }

        suspend fun getFriendsByDate(date: Date):Flow<List<Friend>>{

                val c = Calendar.getInstance()
                c.time=date
                c.set(Calendar.HOUR,0)
                c.set(Calendar.MINUTE,0)
                c.set(Calendar.SECOND,1)
                val startDate: Date = c.time
                val a = Calendar.getInstance()
                a.time=date
                a.set(Calendar.HOUR,23)
                a.set(Calendar.MINUTE,59)
                a.set(Calendar.SECOND,59)
                val endDate: Date = a.time

                return friendDao.getAllFriends()
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