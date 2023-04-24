package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tabletop.tabletopapplication.BuildConfig
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.GameDbAdapter
import com.tabletop.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.tabletop.tabletopapplication.presentationlayer.models.game.Game
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameListViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel

class GameSelectionActivity : AppCompatActivity(R.layout.game_selection) {

    private val materialViewModel by lazy{ ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    )[MaterialViewModel::class.java]}
    private val gameDBViewModel by lazy{ViewModelProvider(
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
        findViewById<ImageView>(R.id.downloaded_materials_button).setOnClickListener{
            getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit().putLong("currentGameId", -1).apply()
            startActivity(Intent(applicationContext, InstallMaterialActivity::class.java))

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
            // This is a new ins    tall (or the user cleared the shared preferences)
            fillRoom()
        } else if (currentVersionCode > savedVersionCode) {
            // This is an upgrade
        }

        prefs.edit().putInt("version_code", currentVersionCode).apply()
    }
    private fun fillRoom() {
        //materialViewModel.getAllMaterialsFromApi()
        gameDBViewModel.getAllGameFromApi()
    }
}
