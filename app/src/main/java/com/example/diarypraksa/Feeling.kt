package com.example.diarypraksa

import androidx.room.*
import java.util.*


@Entity(tableName = "feeling_table")
data class Feeling(
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "sticker_id") var sticker_id: Int,
    @ColumnInfo(name = "description") var description: String
)
{
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    var id: Int = 0
}