package com.example.tabletopapplication.presentationlayer.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.presentationlayer.adapters.GameAdapter
import com.example.tabletopapplication.presentationlayer.viewmodels.GameViewModel
import com.example.tabletopapplication.presentationlayer.models.LoadState

class GameListFragment : Fragment(R.layout.games_recycler_fragment) {

    private val viewModel: GameViewModel = GameViewModel()
    private val gameAdapter = GameAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<RecyclerView>(R.id.game_recycler).apply {
            adapter = gameAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoadState.Initialized -> Unit
                is LoadState.Pending -> Unit
                is LoadState.Success<*> -> gameAdapter.submitItems(state.result as List<GameEntity>)
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