package com.example.cricketscorebook.ui.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.example.cricketscorebook.database.MatchHistory
import com.example.cricketscorebook.databinding.ActivityGameScorerBinding
import com.example.cricketscorebook.databinding.ResultDialogBinding
import java.text.SimpleDateFormat
import java.util.*
import com.example.cricketscorebook.R


class GameScorerActivity : AppCompatActivity() {
    lateinit var teamA: String
    lateinit var teamB: String
    lateinit var batting: String
    lateinit var tOvers: String
    var totalOvers: Int = 0
    var teamAScore: Int = 0
    var teamBScore: Int = 0
    var teamAWicket: Int = 0
    var teamAOver: Int = 0
    var teamBWicket: Int = 0
    var teamBOver: Int = 0
    var teamBBall: Int = 0
    var teamABall: Int = 0
    var inning: Int = 0
    var balls: Int = 0
    var thisOver = ArrayList<String>()
    private var needRun: Int = 0
    private var leftBalls: Int = 0
    var date: String? = null
    var teamWon: String? = null
    var matchHistory: MatchHistory? = null
    lateinit var viewModel: GameScorerViewModel
    var check: Boolean = false
    lateinit var binding: ActivityGameScorerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this)[GameScorerViewModel::class.java]
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_scorer)

        teamA = intent.getStringExtra("teamA")!!
        teamB = intent.getStringExtra("teamB")!!
        batting = intent.getStringExtra("batting")!!
        tOvers = intent.getStringExtra("overs")!!
        binding.tvBattingTeam.text = batting
        totalOvers = if (tOvers == "") {
            10
        } else {
            tOvers.toInt()
        }

        binding.btnQuit.setOnClickListener {
            finish()
        }

        binding.btnUndo.setOnClickListener {
            if (thisOver.size != 0) {
                undoLastMove()
            }
        }

        binding.btnDot.setOnClickListener {
            thisOver.add("0")
            addToScore(0, 0, 1)
        }

        binding.btnOne.setOnClickListener {
            thisOver.add("1")
            addToScore(1, 0, 1)
        }

        binding.btnTwo.setOnClickListener {
            thisOver.add("2")
            addToScore(2, 0, 1)
        }

        binding.btnThree.setOnClickListener {
            thisOver.add("3")
            addToScore(3, 0, 1)
        }

        binding.btnFour.setOnClickListener {
            thisOver.add("4")
            addToScore(4, 0, 1)
        }

        binding.btnSix.setOnClickListener {
            thisOver.add("6")
            addToScore(6, 0, 1)
        }

        binding.btnWide.setOnClickListener {
            thisOver.add("WD")
            addToScore(1, 0, 0)
        }

        binding.btnNoBall.setOnClickListener {
            thisOver.add("NB")
            addToScore(0, 0, 0)
        }

        binding.btnOut.setOnClickListener {
            thisOver.add("W")
            addToScore(0, 1, 1)
        }

    }

    private fun undoLastMove() {
        if (batting == teamA) {
            when {
                thisOver[thisOver.size - 1] == "WD" -> {
                    teamAScore -= 1
                    teamABall -= 0
                    setToWonView(-1, 0)
                }
                thisOver[thisOver.size - 1] == "NB" -> {
                    teamAScore -= 0
                    teamABall -= 0
                    setToWonView(0, 0)
                }
                thisOver[thisOver.size - 1] == "W" -> {
                    teamAScore -= 0
                    teamABall -= 1
                    teamAWicket -= 1
                    balls -= 1
                    setToWonView(0, -1)
                }
                else -> {
                    teamAScore -= thisOver[thisOver.size - 1].toInt()
                    teamABall -= 1
                    balls -= 1
                    setToWonView(-(thisOver[thisOver.size - 1].toInt()), -1)
                }
            }
            binding.tvBattingScore.text = "$teamAScore - $teamAWicket"
            binding.tvBowlingScore.text = "( $teamAOver.$teamABall )"
        } else if (batting == teamB) {
            when {
                thisOver[thisOver.size - 1] == "WD" -> {
                    teamBScore -= 1
                    teamBBall -= 0
                    setToWonView(-1, 0)
                }
                thisOver[thisOver.size - 1] == "NB" -> {
                    teamBScore -= 0
                    teamBBall -= 0
                    setToWonView(0, 0)
                }
                thisOver[thisOver.size - 1] == "W" -> {
                    teamBScore -= 0
                    teamBBall -= 1
                    teamBWicket -= 1
                    balls -= 1
                    setToWonView(0, -1)
                }
                else -> {
                    teamBScore -= thisOver[thisOver.size - 1].toInt()
                    teamBBall -= 1
                    balls -= 1
                    setToWonView(-(thisOver[thisOver.size - 1].toInt()), -1)
                }
            }
            binding.tvBattingScore.text = "$teamBScore - $teamBWicket"
            binding.tvBowlingScore.text = "( $teamBOver.$teamBBall )"
        }
        thisOver.removeAt(thisOver.size - 1)
        displayThisOver()
    }

    private fun addToScore(run: Int, wicket: Int, ball: Int) {
        if (batting == teamA) {
            teamAScore += run
            teamAWicket += wicket
            balls += ball
            teamAOver = balls / 6
            teamABall = balls % 6
            binding.tvBattingScore.text = "$teamAScore - $teamAWicket"
            binding.tvBowlingScore.text = "( $teamAOver.$teamABall )"
            displayThisOver()
            if (inning == 1) {
                setToWonView(run, ball)
                if (teamAScore > teamBScore) {
                    openDialogBox()
                    check = true
                }
            }
            if (isFinish(teamA)) {
                if (inning == 1) {
                    if (!check) {
                        openDialogBox()
                        check = false
                    }
                }
                changeInning(teamA)
            }
        } else if (batting == teamB) {
            teamBScore += run
            teamBWicket += wicket
            balls += ball
            teamBOver = balls / 6
            teamBBall = balls % 6
            binding.tvBattingScore.text = "$teamBScore - $teamBWicket"
            binding.tvBowlingScore.text = "( $teamBOver.$teamBBall )"
            displayThisOver()
            if (inning == 1) {
                setToWonView(run, ball)
                if (teamBScore > teamAScore) {
                    openDialogBox()
                    check = true
                }
            }
            if (isFinish(teamB)) {
                if (inning == 1) {
                    if (!check) {
                        openDialogBox()
                        check = false
                    }
                }
                changeInning(teamB)
            }
        }
    }

    private fun setToWonView(run: Int, ball: Int) {
        needRun -= run
        leftBalls -= ball
        binding.tvToWin.text = "$batting need $needRun in $leftBalls balls"
    }

    private fun openDialogBox() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val dialogView = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(this),
            R.layout.result_dialog, null, false
        ) as ResultDialogBinding
        dialog.setContentView(dialogView.root)

        when {
            teamAScore > teamBScore -> {
                dialogView.result.text = "$teamA Won the Match"
                teamWon = teamA
            }
            teamAScore == teamBScore -> {
                dialogView.resultTrophy.setImageResource(R.drawable.ic_trophy_tie)
                dialogView.result.text = "That was a tie!"
                teamWon = "Tie"
            }
            else -> {
                dialogView.result.text = "$teamB Won the Match"
                teamWon = teamB
            }
        }
        dialog.setCancelable(false)
        dialog.show()
        dialogView.btnQuit.setOnClickListener {
            dialog.dismiss()
            finish()
        }
        setDate()
        setValuesForMatchHistory()
    }

    private fun setValuesForMatchHistory() {
        val matchHistory = MatchHistory(
            0,
            date!!,
            teamA,
            teamB,
            teamAScore,
            teamBScore,
            teamAWicket,
            teamBWicket,
            "$teamAOver.$teamABall",
            "$teamBOver.$teamBBall",
            teamWon!!
        )
        viewModel.insertMatchHistory(matchHistory)
    }

    private fun setDate() {
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val calander = Calendar.getInstance()
        date = sdf.format(calander.time)
    }

    private fun changeInning(battingTeam: String) {
        inning = 1
        binding.cdTeamAScore.visibility = View.VISIBLE
        binding.cdToWinBoard.visibility = View.VISIBLE
        binding.tvTeamA.text = battingTeam
        if (battingTeam == teamA) {
            batting = teamB
            binding.tvBattingTeam.text = teamB
            binding.tvTeamAScore.text = "$teamAScore - $teamAWicket"
            binding.tvTeamAOvers.text = "( $teamAOver.$teamABall )"
            binding.tvBattingScore.text = "$teamBScore - $teamBWicket"
            binding.tvBowlingScore.text = "( $teamBOver.$teamBBall )"
            balls = 0
            needRun = teamAScore + 1
            leftBalls = totalOvers * 6
            binding.tvToWin.text = "$batting need $needRun in $leftBalls balls"
            displayThisOver()
        } else {
            batting = teamA
            binding.tvBattingTeam.text = teamA
            binding.tvTeamAScore.text = "$teamBScore - $teamBWicket"
            binding.tvTeamAOvers.text = "( $teamBOver.$teamBBall )"
            binding.tvBattingScore.text = "$teamAScore - $teamAWicket"
            binding.tvBowlingScore.text = "( $teamAOver.$teamABall )"
            balls = 0
            needRun = teamBScore + 1
            leftBalls = totalOvers * 6
            binding.tvToWin.text = "$batting need $needRun in $leftBalls balls"
            displayThisOver()
        }
    }

    private fun displayThisOver() {
        val builder = StringBuilder()
        for (details in thisOver) {
            builder.append("$details ")
        }
        binding.tvThisOverScore.text = builder.toString()
        if (balls % 6 == 0) {
            thisOver.removeAll(thisOver)
        }
    }

    private fun isFinish(team: String): Boolean {
        if (team == teamA) {
            if ((balls == totalOvers * 6) || teamAWicket == 10) {
                return true
            }
        } else {
            if ((balls == totalOvers * 6) || teamBWicket == 10) {
                return true
            }
        }
        return false
    }
}