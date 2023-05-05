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
import com.tabletop.tabletopapplication.presentationlayer.adapters.GameAdapter
import com.tabletop.tabletopapplication.presentationlayer.contracts.IntentGameContract
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel


class GameSelectionActivity : AppCompatActivity(R.layout.game_selection) {

    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[GameDBViewModel::class.java]
    }

    private lateinit var gameAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectionActivityLauncher = registerForActivityResult(IntentGameContract()) { result ->
            result?.let {
                gameAdapter.replaceGame(gameAdapter.findGameById(result.id), result)
            }
        }

        gameAdapter = GameAdapter(clickAction = selectionActivityLauncher)

        findViewById<RecyclerView>(R.id.game_recycler).apply {
            adapter = gameAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        findViewById<ImageView>(R.id.downloaded_materials_button).setOnClickListener {
            getSharedPreferences("MyPrefsFile", MODE_PRIVATE).edit()
                .putLong("currentGameId", -1)
                .apply()
            startActivity(Intent(applicationContext, InstallMaterialActivity::class.java))
            finish()
        }

        val addActivityLauncher = registerForActivityResult(IntentGameContract()) { result ->
            result?.let {
                gameAdapter.addGame(result)
            }
        }
        findViewById<ImageView>(R.id.add_game_button).setOnClickListener {
            addActivityLauncher.launch(Intent(applicationContext, GameEditActivity::class.java).apply {
                putExtra("id", -1)
            })
        }


    }

    private fun fillRecycler() {

    }

    private fun DBCheck() {

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
//        viewModel.getAllGameFromApi()
    }
}
