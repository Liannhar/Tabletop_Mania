package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.presentationlayer.adapters.GameAdapter
import com.example.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.example.tabletopapplication.presentationlayer.models.LoadState
import com.example.tabletopapplication.presentationlayer.viewmodels.GameListViewModel

class GameSelectionActivity : AppCompatActivity(R.layout.game_selection) {

    private val viewModel: GameListViewModel = GameListViewModel()
    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        gameAdapter = GameAdapter()
        findViewById<RecyclerView>(R.id.game_recycler).apply {
            adapter = gameAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        // Clicks
        findViewById<ImageView>(R.id.add_game_button).setOnClickListener {
            val intent = Intent(this, GameEditActivity::class.java)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.EDIT.value)
        }

        // Observes
        viewModel.LDstate.observe(this) { state ->
            when (state) {
                is LoadState.Initialized -> Unit
                is LoadState.Pending -> Unit
                is LoadState.Success<*> ->
                    when(state.result) {
                        is GameEntity -> gameAdapter.addGame(state.result)
                        is ArrayList<*> -> gameAdapter.addListGames(state.result as List<GameEntity>)
                    }
                is LoadState.Error -> Unit
            }
        }

        viewModel.load()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            ACTIVITY_REQUEST_CODE.PREVIEW.value -> {
                when(resultCode) {
                    RESULT_OK -> {
                        val game = data?.extras?.getParcelable("Game", GameEntity::class.java)
                        gameAdapter.changeGame(game!!)
                        // TODO Добавить изменение игры, если есть
                    }
                }
            }
            ACTIVITY_REQUEST_CODE.EDIT.value -> {
                when(resultCode) {
                    RESULT_OK -> {
                        Log.d("ASDASD", resultCode.toString())
                        val newGame = data?.extras?.getParcelable("Game", GameEntity::class.java)
                        gameAdapter.addGame(newGame!!)
                        // TODO Добавить новую игру
                    }
                }
            }
        }
    }
}