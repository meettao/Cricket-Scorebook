package com.example.cricketscorebook.repository

import com.example.cricketscorebook.database.HistoryDatabase
import com.example.cricketscorebook.database.MatchHistory

class HistoryRepository(val database: HistoryDatabase) {

    fun insertMatchHistory(history: MatchHistory) {
        database.matchHistoryDAO.insert(history)
    }

    fun getMatchHistory(): List<MatchHistory> {
        return database.matchHistoryDAO.getAll()
    }
}