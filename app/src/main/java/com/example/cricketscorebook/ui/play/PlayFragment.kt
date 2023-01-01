package com.example.cricketscorebook.ui.play

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.cricketscorebook.databinding.FragmentPlayBinding
import com.example.cricketscorebook.ui.activity.GameScorerActivity


class PlayFragment : Fragment() {

    private lateinit var playViewModel: PlayViewModel
    lateinit var team1: EditText
    lateinit var team2: EditText

    lateinit var binding: FragmentPlayBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        team1 = binding.etTeamA
        team2 = binding.etTeamB

        team1.addTextChangedListener(textWatcher1)
        team2.addTextChangedListener(textWatcher2)

        binding.btnStartMatch.setOnClickListener {
            if (binding.etTeamA.text.isNullOrEmpty() || binding.etTeamB.text.isNullOrEmpty()) {
                Toast.makeText(context, "Please Enter Team Name", Toast.LENGTH_SHORT).show()
            } else if (binding.etOver.text.isNullOrEmpty() ) {
                Toast.makeText(context, "Please Enter Overs", Toast.LENGTH_SHORT).show()
            } else if (binding.etTeamA.text.toString() == binding.etTeamB.text.toString()) {
                Toast.makeText(context, "Please enter different name of teams", Toast.LENGTH_SHORT).show()
            } else {
                val checkedRadioButtonId: Int = binding.rgBatting.checkedRadioButtonId
                val radioButton = view?.findViewById<RadioButton>(checkedRadioButtonId)

                val intent = Intent(context, GameScorerActivity::class.java)
                intent.putExtra("teamA", binding.etTeamA.text.toString())
                intent.putExtra("teamB", binding.etTeamB.text.toString())
                intent.putExtra("batting", radioButton?.text.toString())
                intent.putExtra("overs", binding.etOver.text.toString())
                startActivity(intent)
            }
        }
    }

    private var textWatcher1 = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.rbTeamA.text = p0
        }

    }

    private var textWatcher2 = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            binding.rbTeamB.text = p0
        }
    }

}
