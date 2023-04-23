package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.BuildConfig
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.presentationlayer.adapters.GameAdapter
import com.example.tabletopapplication.presentationlayer.adapters.GameDbAdapter
import com.example.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.example.tabletopapplication.presentationlayer.models.LoadState
import com.example.tabletopapplication.presentationlayer.models.game.Game
import com.example.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.GameListViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import java.util.concurrent.CountDownLatch


class GameSelectionActivity : AppCompatActivity(R.layout.game_selection) {

    val materialViewModel by lazy{ ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    )[MaterialViewModel::class.java]}
    val gameDBViewModel by lazy{ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    )[GameDBViewModel::class.java]

    }


    private val viewModel: GameListViewModel = GameListViewModel()
    private val gameAdapter = GameDbAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSharedPreferences("MyPrefsFile", MODE_PRIVATE).getLong("currentGameId", -1)

        findViewById<RecyclerView>(R.id.game_recycler).apply {
            adapter = gameAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        // Clicks
        findViewById<ImageView>(R.id.add_game_button).setOnClickListener {
            val gameId = gameDBViewModel.addGame(Game("Set Title","Set Description",""))
            getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit().putLong("currentGameId", -1).apply()
            gameId.observe(this){id->
                val intent = Intent(this, GameEditActivity::class.java)
                getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit().putLong("currentGameId", id).apply()
                startActivityForResult(intent, ACTIVITY_REQUEST_CODE.EDIT.value)
            }
        }

        // Observes
        /*viewModel.LDstate.observe(this) { state ->
            when (state) {
                is LoadState.Initialized -> Unit
                is LoadState.Pending -> Unit
                is LoadState.Success<*> ->
                    when(state.result) {
                        is GameEntity -> gameAdapter.addGame(state.result)
                        is ArrayList<*> -> gameAdapter.addListGames(state.result as ArrayList<GameEntity>)
                    }
                is LoadState.Error -> Unit
            }
        }

        viewModel.load()*/
        DBCheck()
        fillRecycler()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        /*when(requestCode) {
            ACTIVITY_REQUEST_CODE.PREVIEW.value -> {
                when(resultCode) {
                    RESULT_OK -> {
                        val game = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            data?.extras?.getParcelable("Game", GameEntity::class.java)
                        } else {
                            data?.getParcelableExtra("Game")
                        }
                        gameAdapter.changeGame(game!!)
                        // TODO("Добавить изменение игры, если есть)
                    }
                }
            }
            ACTIVITY_REQUEST_CODE.EDIT.value -> {
                when(resultCode) {
                    RESULT_OK -> {
                        val newGame = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            data?.extras?.getParcelable("Game", GameEntity::class.java)
                        } else {
                            data?.getParcelableExtra("Game")
                        }
                        gameAdapter.addGame(newGame!!)
                        // TODO("Добавить новую игру")
                    }
                }
            }
        }*/
    }

    private fun fillRecycler() {
        gameDBViewModel.getAllGame().observe(this){
            game-> gameAdapter.addListGames(game)
        }
    }

    private fun DBCheck(){

        val currentVersionCode = BuildConfig.VERSION_CODE
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val savedVersionCode = prefs.getInt("version_code", -1)
        if (savedVersionCode == -1) {
            // This is a new install (or the user cleared the shared preferences)
            fillRoom()
        } else if (currentVersionCode > savedVersionCode) {
            // This is an upgrade
        }

        prefs.edit().putInt("version_code", currentVersionCode).apply()
    }
    private fun fillRoom() {
        materialViewModel.getAllMaterialsFromApi()
        gameDBViewModel.getAllGameFromApi()
    }
}
