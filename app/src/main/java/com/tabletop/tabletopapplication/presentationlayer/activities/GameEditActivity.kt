package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.presentationlayer.adapters.DelegateMaterialsAdapter
import com.tabletop.tabletopapplication.presentationlayer.contracts.IntentGameContract
import com.tabletop.tabletopapplication.presentationlayer.contracts.IntentMaterialContract
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameEditActivity : AppCompatActivity(R.layout.activity_edit_game) {

    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DBViewModel::class.java]
    }

    private var currentGame: Game = Game()
    private val delegateMaterialsAdapter by lazy {
        DelegateMaterialsAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentGame.id = intent?.run {
            getIntExtra("id", -1)
        } ?: -1

        val editGameTitle = findViewById<TextView>(R.id.activity_edit_game__title)
        val editGameDescription = findViewById<TextView>(R.id.activity_edit_game__description)
        val editGameImage = findViewById<ImageView>(R.id.activity_edit_game__image)

        findViewById<RecyclerView>(R.id.activity_edit_game__rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = delegateMaterialsAdapter
        }

        findViewById<ImageView>(R.id.activity_edit_game__save_button).setOnClickListener {
            lifecycleScope.launch {
                databaseVM.add(currentGame).join()

                databaseVM.addListMaterialToGame(currentGame, delegateMaterialsAdapter.getMaterials())

                if (currentGame.id == 0)
                    currentGame.id = databaseVM.getCountGames()

                setResult(RESULT_OK, Intent().apply {
                    putExtra("Game", currentGame)
                })
                finish()
            }
        }

        findViewById<ImageView>(R.id.activity_edit_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        val addMaterialLauncher = registerForActivityResult(IntentMaterialContract()) { result ->
            result?.apply {
                delegateMaterialsAdapter.add(this)
            }
        }

        findViewById<ImageView>(R.id.activity_edit_game__add_button).setOnClickListener {
            addMaterialLauncher.launch(Intent(this, ChooseMaterialActivity::class.java).apply {
                putExtra("id", currentGame.id)
            })
        }

        val editGameActivityLauncher = registerForActivityResult(IntentGameContract()) { result ->
            lifecycleScope.launch {
                result?.apply {
                    currentGame = this
                    delegateMaterialsAdapter.updateAll(databaseVM.getMaterialsByGame(currentGame.id))

                    editGameTitle.text = currentGame.name
                    editGameDescription.text = currentGame.description

                    Glide.with(editGameImage)
                        .load(currentGame.image)
                        .centerCrop()
                        .placeholder(R.drawable.baseline_downloading_24)
                        .error(R.drawable.baseline_error_outline_24)
                        .into(editGameImage)
                }
            }
        }

        findViewById<CardView>(R.id.activity_edit_game__edit_button).setOnClickListener {
            editGameActivityLauncher.launch(Intent(this, GameEditPropertiesActivity::class.java).apply {
                putExtra("Game", currentGame)
            })
        }

        lifecycleScope.launch {

            currentGame = databaseVM.getGame(currentGame.id)?.apply {
                delegateMaterialsAdapter.addAll(databaseVM.getMaterialsByGame(id))
            } ?: Game("Title", "Description", null)

            editGameTitle.text = currentGame.name
            editGameDescription.text = currentGame.description

            Glide.with(editGameImage)
                .load(currentGame.image)
                .centerCrop()
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_error_outline_24)
                .into(editGameImage)
        }

    }

}