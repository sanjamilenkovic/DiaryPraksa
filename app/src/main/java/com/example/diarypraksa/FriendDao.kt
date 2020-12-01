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
    fun getAllFriends(): Flow<List<Friend>>

    @Query("SELECT * FROM friend_table WHERE id = :id")
    suspend fun getFriendById(id: Int): Friend

    //radi i update i insert, ako utvrdi da podatak postoji, radi REPLACE, inace radi INSERT
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(friend: Friend): Long

    @Query("DELETE FROM friend_table")
    suspend fun deleteAll()

    @Query("DELETE FROM friend_table WHERE id = :id")
    suspend fun deleteFriendById(id: Int)

    @Query("UPDATE friend_table SET image=:newImage, name=:newName, last_name=:newLastName, email =:newEmail, notes=:newNotes, number=:newNumber WHERE id=:id")
    suspend fun updateFriend(
        id: Int,
        newImage: String,
        newName: String,
        newLastName: String,
        newEmail: String,
        newNotes: String,
        newNumber: String
    )

    @Query("UPDATE friend_table SET image=:newImage, name=:newName, last_name=:newLastName, email =:newEmail, notes=:newNotes, number=:newNumber WHERE id=:id")
    suspend fun updateFriendById(
        id: Int,
        newImage: String,
        newName: String,
        newLastName: String,
        newEmail: String,
        newNotes: String,
        newNumber: String
    )

    @Query("SELECT * FROM friend_table WHERE date BETWEEN :startOfDay AND :endOfDay")
    suspend fun getFriendByDate(startOfDay: Date, endOfDay: Date): Friend }

