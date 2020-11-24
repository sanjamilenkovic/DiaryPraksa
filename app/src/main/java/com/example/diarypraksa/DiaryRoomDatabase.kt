package com.example.diarypraksa

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Feeling::class, Friend::class), version = 1, exportSchema = false)
public abstract class DiaryRoomDatabase : RoomDatabase() {


        abstract fun feelingDao(): FeelingDao
        abstract fun friendDao(): FriendDao

        companion object {
            // Singleton prevents multiple instances of database opening at the
            // same time.
            @Volatile
            private var INSTANCE: DiaryRoomDatabase? = null

            fun getDatabase(context: Context, applicationScope: CoroutineScope): DiaryRoomDatabase {
                // if the INSTANCE is not null, then return it,
                // if it is, then create the database
                return INSTANCE ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DiaryRoomDatabase::class.java,
                        "diary_database"
                    ).build()
                    INSTANCE = instance
                    // return instance
                    instance
                }
            }
        }
    }