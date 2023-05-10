package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tabletop.tabletopapplication.BuildConfig
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.GameAdapter
import com.tabletop.tabletopapplication.presentationlayer.contracts.IntentGameContract
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.launch


class GameSelectionActivity : AppCompatActivity(R.layout.activity_game_selection) {

    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DBViewModel::class.java]
    }

    private lateinit var gamesAdapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectionLauncher = registerForActivityResult(IntentGameContract()) { result ->
            result?.let {
                gamesAdapter.replaceGame(gamesAdapter.findPositionById(result.id), it)
            }
        }
        gamesAdapter = GameAdapter(clickAction = selectionLauncher)

        findViewById<RecyclerView>(R.id.game_recycler).apply {
            adapter = gamesAdapter
            layoutManager = GridLayoutManager(context, 2)
        }

        findViewById<ImageView>(R.id.downloaded_materials_button).setOnClickListener {
            startActivity(Intent(this, InstallMaterialActivity::class.java))
        }

        val addActivityLauncher = registerForActivityResult(IntentGameContract()) { result ->
            result?.let {
                gamesAdapter.addGame(result)
            }
        }
        findViewById<ImageView>(R.id.add_game_button).setOnClickListener {
            addActivityLauncher.launch(Intent(this, GameEditActivity::class.java).apply {
                putExtra("id", -1)
            })
        }

        fillRecycler()
    }

    private fun fillRecycler() {
        lifecycleScope.launch {
            gamesAdapter.addAll(databaseVM.getAllGames())
        }
    }
}
