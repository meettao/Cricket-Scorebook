package com.example.cricketscorebook.ui.history

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscorebook.database.MatchHistory

class HistoryAdapter : RecyclerView.Adapter<HistoryViewHolder>(){

    var data = listOf<MatchHistory?>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder.create(parent)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val item = data[position]
        if (item != null) {
            (holder as HistoryViewHolder).bind(item)
        }
    }
}