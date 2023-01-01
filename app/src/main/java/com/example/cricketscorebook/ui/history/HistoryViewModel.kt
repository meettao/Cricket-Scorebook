package com.example.cricketscorebook.ui.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.cricketscorebook.database.HistoryDatabase
import com.example.cricketscorebook.database.MatchHistory
import com.example.cricketscorebook.repository.HistoryRepository
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class HistoryViewModel(context: Application) : AndroidViewModel(context) {

    private var parentJob = SupervisorJob()
    var repository = HistoryRepository(HistoryDatabase.getInstance(context))

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)
    private val historyList: MutableLiveData<List<MatchHistory>> = MutableLiveData()

    fun matchHistory(): LiveData<List<MatchHistory>> {
        return historyList
    }

    fun fetchAllMatchHistory() {
        scope.launch {
            withContext(Dispatchers.IO) {
                historyList.postValue(repository.getMatchHistory())
            }
        }
    }

}