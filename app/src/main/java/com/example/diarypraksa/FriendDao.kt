package com.example.diarypraksa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao

interface FriendDao {

    @Query("SELECT * FROM friend_table")
    fun getAllFriends() : Flow<List<Friend>>

    @Query("SELECT * FROM friend_table WHERE date BETWEEN :startDate AND :endDate")
    fun getFriendsByDate(startDate: Date, endDate: Date) : Flow<List<Friend>>

    @Query("SELECT * FROM friend_table WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getFriendByDate(startDate: Date, endDate: Date) :Friend


    @Query("SELECT * FROM friend_table WHERE id = :id")
    suspend fun getFriendById(id : Int) : Friend

    //radi i update i insert, ako utvrdi da podatak postoji, radi REPLACE, inace radi INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(friend : Friend)

    @Query("DELETE FROM friend_table")
    suspend fun deleteAll()

    @Query("DELETE FROM friend_table WHERE id = :id")
    suspend fun deleteFriendById(id : Int)

}