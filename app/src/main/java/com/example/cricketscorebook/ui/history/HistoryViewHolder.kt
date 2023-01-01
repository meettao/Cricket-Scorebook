package com.example.cricketscorebook.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketscorebook.R
import com.example.cricketscorebook.database.MatchHistory

class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val teamAName = view.findViewById<TextView>(R.id.tv_team_a_name)
    private val teamAScore = view.findViewById<TextView>(R.id.tv_team_a_score)
    private val teamBName = view.findViewById<TextView>(R.id.tv_team_b_name)
    private val teamBScore = view.findViewById<TextView>(R.id.tv_team_b_score)
    private val result = view.findViewById<TextView>(R.id.tv_result)
    private val date= view.findViewById<TextView>(R.id.tv_date)

    private var matchHistory: MatchHistory? = null

    fun bind(matchHistory: MatchHistory?) {
        if (matchHistory != null) {

            showMatchHistory(matchHistory)
        }
    }

    private fun showMatchHistory(matchHistory: MatchHistory) {
        teamAName.text = matchHistory.teamA
        teamBName.text = matchHistory.teamB
        teamAScore.text = "${matchHistory.teamAScore} - ${matchHistory.teamAWickets} (${matchHistory.teamAOvers})"
        teamBScore.text = "${matchHistory.teamBScore} - ${matchHistory.teamBWickets} (${matchHistory.teamBOvers})"
        if (matchHistory.teamWon == "Tie"){
            result.text = "That was Tie"
        } else {
            result.text = "${matchHistory.teamWon} Won The Match"
        }
        date.text = matchHistory.date
    }

    companion object {
        fun create(parent: ViewGroup): HistoryViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.history_detail_item, parent, false)
            return HistoryViewHolder(view)
        }
    }
}