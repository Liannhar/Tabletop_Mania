package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.DelegateMaterialsAdapter
import com.tabletop.tabletopapplication.presentationlayer.contracts.IntentGameContract
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.launch

class GamePreviewActivity : AppCompatActivity(R.layout.activity_preview_game) {

    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DBViewModel::class.java]
    }

    private var currentGame = Game()
    private val delegateMaterialsAdapter by lazy {
        DelegateMaterialsAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentGame.id = intent.extras?.run {
            getInt("id", -1)
        } ?: -1

        val previewGameTitle = findViewById<TextView>(R.id.activity_preview_game__title)
        val previewGameDescription = findViewById<TextView>(R.id.activity_preview_game__description)
        val previewGameImage = findViewById<ImageView>(R.id.activity_preview_game__image)

        findViewById<RecyclerView>(R.id.activity_preview_game__rv).apply {
            adapter = delegateMaterialsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        findViewById<ImageView>(R.id.activity_preview_game__back_button).setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("Game", currentGame)
            })
            finish()
        }

        val editActivityLauncher = registerForActivityResult(IntentGameContract()) { result ->
            result?.apply {

                currentGame = this

                previewGameTitle.text = currentGame.name
                previewGameDescription.text = currentGame.description

                Glide.with(previewGameImage)
                    .load(currentGame.image)
                    .centerCrop()
                    .placeholder(R.drawable.baseline_downloading_24)
                    .error(R.drawable.baseline_error_outline_24)
                    .into(previewGameImage)
            }
        }

        findViewById<ImageView>(R.id.activity_preview_game__edit_button).setOnClickListener {
            editActivityLauncher.launch(Intent(this, GameEditActivity::class.java).apply {
                putExtra("id", currentGame.id)
            })
        }

        lifecycleScope.launch {
            databaseVM.getGame(currentGame.id)?.let {
                currentGame = it
                delegateMaterialsAdapter.addAll(databaseVM.getMaterialsByGame(it.id))
            }

            previewGameTitle.text = currentGame.name
            previewGameDescription.text = currentGame.description

            Glide.with(previewGameImage)
                .load(currentGame.image)
                .centerCrop()
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_error_outline_24)
                .into(previewGameImage)
        }
    }
}

