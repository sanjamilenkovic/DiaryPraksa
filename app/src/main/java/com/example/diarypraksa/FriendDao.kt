package com.example.diarypraksa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FriendDao {

    @Query("SELECT * FROM friend_table")
    fun getAllFriends() : Flow<List<Friend>>

    @Query("SELECT * FROM friend_table WHERE id = :id")
    suspend fun getFriendById(id : Int)

    //radi i update i insert, ako utvrdi da podatak postoji, radi REPLACE, inace radi INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(friend : Friend)

    @Query("DELETE FROM friend_table")
    suspend fun deleteAll()

    @Query("DELETE FROM friend_table WHERE id = :id")
    suspend fun deleteFriendById(id : Int)

}