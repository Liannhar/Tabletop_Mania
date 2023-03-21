package com.example.tabletopapplication.presentationlayer.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.adapters.GameAdapter
import com.example.tabletopapplication.presentationlayer.models.GameViewModel
import com.example.tabletopapplication.presentationlayer.models.LoadState

class GameListFragment: Fragment(R.layout.games_recycler_fragment) {

    val viewModel by viewModels<GameViewModel>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recycler = view.findViewById<RecyclerView>(R.id.game_recycler)
        val adapter = GameAdapter()
        recycler.adapter = adapter
        recycler.layoutManager = GridLayoutManager(requireContext(), 2)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when(state) {
                is LoadState.Initialized -> Unit
                is LoadState.Pending -> Unit
                is LoadState.Success -> adapter.submitItems(state.result)
                is LoadState.Error -> Unit
            }
        }

        viewModel.load()
    }

    companion object {

        fun newInstance(): Fragment = GameListFragment()

        const val TAG = "GameListFragment"
    }
}