package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.DelegateMaterialsAdapter
import com.tabletop.tabletopapplication.presentationlayer.adapters.HistoryAdapter
import com.tabletop.tabletopapplication.presentationlayer.contracts.IntentGameContract
import com.tabletop.tabletopapplication.presentationlayer.fragments.HistoryFragment
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.Dispatchers
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GamePreviewViewModel
import kotlinx.coroutines.launch
import okhttp3.Dispatcher

class GamePreviewActivity : AppCompatActivity(R.layout.activity_preview_game) {

    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DBViewModel::class.java]
    }

    private val gamePreviewVM: GamePreviewViewModel by viewModels()

    private var currentGame = Game()
    private val delegateMaterialsAdapter by lazy {
        DelegateMaterialsAdapter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentGame.id = intent.extras?.run {
            getInt("id", -1)
        } ?: -1
        Log.i("WINWIN",currentGame.id.toString()+"P")
        val previewGameTitle = findViewById<TextView>(R.id.activity_preview_game__title)
        val previewGameDescription = findViewById<TextView>(R.id.activity_preview_game__description)
        val previewGameImage = findViewById<ImageView>(R.id.activity_preview_game__image)

        findViewById<RecyclerView>(R.id.activity_preview_game__rv).apply {
            adapter = delegateMaterialsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        findViewById<ImageView>(R.id.activity_preview_game__back_button).setOnClickListener {

            databaseVM.viewModelScope.launch(Dispatchers.IO) {
                databaseVM.updateMaterialsAtGame(currentGame.id, delegateMaterialsAdapter.getMaterials())
            }

            setResult(RESULT_OK, Intent().apply {
                putExtra("Game", currentGame)
            })
            finish()
        }

        val editActivityLauncher = registerForActivityResult(IntentGameContract()) { result ->
            lifecycleScope.launch {

                delegateMaterialsAdapter.updateAll(databaseVM.getMaterialsByGame(currentGame.id))

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

        val historyView = findViewById<LinearLayout>(R.id.history)
        val historyFlagLine = findViewById<ImageView>(R.id.history_flag_line)
        val historyButton = findViewById<ImageView>(R.id.show_history)
        val historyAdapter = HistoryAdapter()

        findViewById<RecyclerView>(R.id.history_rv).apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = historyAdapter
        }

        historyButton.setOnClickListener {
            if (historyView.isVisible) {
                historyView.isVisible = false
                historyFlagLine.isVisible = false
                historyButton.setImageResource(R.drawable.history_down)
            } else {
                historyView.isVisible = true
                historyFlagLine.isVisible = true
                historyButton.setImageResource(R.drawable.history_up)
            }
        }

        gamePreviewVM.newHistory.observe(this) { newHistory ->
            historyAdapter.add(newHistory)
        }

        findViewById<ImageView>(R.id.add_history_button).setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.history_place, HistoryFragment())
                .commit()
        }


    }
}
