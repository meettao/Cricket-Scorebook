package com.example.cricketscorebook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MatchHistory::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {

    abstract val matchHistoryDAO: MatchHistoryDAO

    companion object {

        @Volatile
        private var INSTANCE: HistoryDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): HistoryDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryDatabase::class.java, "History.db"
                    ).build()
                }
                return INSTANCE!!
            }
        }
    }
}