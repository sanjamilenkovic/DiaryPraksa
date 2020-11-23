package com.example.diarypraksa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface FeelingDao {

    @Query("SELECT * FROM feeling_table")
    fun getAllFeelings() : Flow<List<Feeling>>

    @Query("SELECT * FROM feeling_table WHERE id = :id")
    suspend fun getFeelingById(id : Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(feeling : Feeling)

    @Query("DELETE FROM feeling_table")
    suspend fun deleteAll()

    @Query("DELETE FROM feeling_table WHERE id = :id")
    suspend fun deleteFeelingById(id : Int)
}