package com.example.diarypraksa

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.util.*


@Dao
interface FeelingDao {

    @Query("SELECT * FROM feeling_table")
    fun getAllFeelings() : Flow<List<Feeling>>

    @Query("SELECT * FROM feeling_table WHERE id = :id")
    suspend fun getFeelingById(id: Int) : Feeling

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(feeling: Feeling) : Long

    @Query("DELETE FROM feeling_table")
    suspend fun deleteAll()

    @Query("DELETE FROM feeling_table WHERE id = :id")
    suspend fun deleteFeelingById(id: Int)

    @Query("UPDATE feeling_table SET description=:description, sticker_id =:sticker_id  WHERE id = :id")
    suspend fun updateFeeling(description : String, sticker_id : Int, id : Int)

    @Query("SELECT * FROM feeling_table WHERE date BETWEEN :startDate AND :endDate")
    suspend fun getFeelingByDate(startDate: Date, endDate: Date):Feeling
}