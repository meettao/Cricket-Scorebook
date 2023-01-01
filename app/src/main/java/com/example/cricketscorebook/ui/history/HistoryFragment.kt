package com.example.cricketscorebook.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketscorebook.database.MatchHistory
import com.example.cricketscorebook.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private val adapter = HistoryAdapter()

    lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        historyViewModel =
            ViewModelProviders.of(this).get(HistoryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
    }

    private fun initAdapter() {
        binding.rvHistory.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHistory.adapter = adapter
        historyViewModel.fetchAllMatchHistory()
        historyViewModel.matchHistory().observe(this.viewLifecycleOwner, matchHistoryObserver)
    }
     private val matchHistoryObserver = Observer<List<MatchHistory>> {
         if (it == null || it.isEmpty()){
             adapter.data = emptyList()
             binding.rvHistory.visibility = View.GONE
             binding.emptyHistory.visibility = View.VISIBLE
         } else {
             adapter.data = it
         }
     }
}
