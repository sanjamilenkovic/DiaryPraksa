package com.example.diarypraksa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "friend_table")
data class Friend(
    @ColumnInfo(name = "image") var image : String,
    @ColumnInfo(name = "name") var name : String ,
    @ColumnInfo(name = "last_name") var lastName : String,
    @ColumnInfo(name = "date") var date : Date,
    @ColumnInfo(name = "email") var email : String,
    @ColumnInfo(name = "notes") var notes : String,
    @ColumnInfo(name = "number") var number : String)
{
    @PrimaryKey (autoGenerate = true) @ColumnInfo(name = "ID") var id : Int = 0
}