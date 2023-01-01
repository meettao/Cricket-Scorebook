package com.example.cricketscorebook.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MatchHistoryDAO {

    @Insert
    fun insert(history: MatchHistory)

    @Query("SELECT * FROM cricket_match_history_table ORDER BY id DESC")
    fun getAll(): List<MatchHistory>
}
