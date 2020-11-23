package com.example.diarypraksa

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


@Entity(tableName = "friend_table")
data class Friend(@PrimaryKey (autoGenerate = true) @ColumnInfo(name = "ID") val id : Int,
                  @ColumnInfo(name = "image") val image : String,
                  @ColumnInfo(name = "name") val name : String ,
                  @ColumnInfo(name = "last_name") val lastName : String,
                  @ColumnInfo(name = "date") val date : Date,
                  @ColumnInfo(name = "email") val email : String,
                  @ColumnInfo(name = "notes") val notes : String,
                  @ColumnInfo(name = "number") val number : String)
{

}