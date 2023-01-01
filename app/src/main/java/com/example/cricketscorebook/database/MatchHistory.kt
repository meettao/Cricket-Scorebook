package com.example.cricketscorebook.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cricket_match_history_table")
data class MatchHistory(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "team_a_name")
    var teamA: String,

    @ColumnInfo(name = "team_b_name")
    var teamB: String,

    @ColumnInfo(name = "team_a_score")
    var teamAScore: Int = 0,

    @ColumnInfo(name = "team_b_score")
    var teamBScore: Int = 0,

    @ColumnInfo(name = "team_a_wicket")
    var teamAWickets: Int = 0,

    @ColumnInfo(name = "team_b_wicket")
    var teamBWickets: Int = 0,

    @ColumnInfo(name = "team_a_overs")
    var teamAOvers: String,

    @ColumnInfo(name = "team_b_overs")
    var teamBOvers: String,

    @ColumnInfo(name = "winning_team")
    var teamWon: String

    )
