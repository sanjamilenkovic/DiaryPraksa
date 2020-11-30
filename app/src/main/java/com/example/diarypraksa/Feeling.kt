package com.example.diarypraksa

import androidx.room.*
import java.util.*


@Entity(tableName = "feeling_table")
data class Feeling(
    @ColumnInfo(name = "date") val date: Date,
    @ColumnInfo(name = "sticker_id") val sticker_id: Int,
    @ColumnInfo(name = "description") val description: String

)
{

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var ID: Int =0

}