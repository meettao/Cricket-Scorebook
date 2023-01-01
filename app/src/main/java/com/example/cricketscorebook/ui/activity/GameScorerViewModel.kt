package com.example.cricketscorebook.ui.activity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.cricketscorebook.database.HistoryDatabase
import com.example.cricketscorebook.database.MatchHistory
import com.example.cricketscorebook.repository.HistoryRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class GameScorerViewModel(context: Application) : AndroidViewModel(context) {

    private var parentJob = SupervisorJob()
    var repository = HistoryRepository(HistoryDatabase.getInstance(context))

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    fun insertMatchHistory(matchHistory: MatchHistory) {
        scope.launch {
            withContext(Dispatchers.IO) {
                repository.insertMatchHistory(matchHistory)
            }
        }
    }


}