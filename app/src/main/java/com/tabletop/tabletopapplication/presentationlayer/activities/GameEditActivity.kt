package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
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
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialsAdapter
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.EntityROOM
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GameEditActivity : AppCompatActivity(R.layout.activity_edit_game) {

    private val viewModel by lazy { ViewModelProvider(this)[GameDBViewModel::class.java] }

    private var game: Game = Game()
    private val differentMaterialsAdapter by lazy { MaterialsAdapter(this, viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        var gameId: Int = -1
        if (intent.extras != null) {
            gameId = intent.getIntExtra("id", -1)
        }

        val editGameTitle = findViewById<TextView>(R.id.activity_edit_game__title)
        val editGameImage = findViewById<ImageView>(R.id.activity_edit_game__image)
        val editGameDescription = findViewById<TextView>(R.id.activity_edit_game__description)

        findViewById<RecyclerView>(R.id.activity_edit_game__rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = differentMaterialsAdapter
        }

        findViewById<ImageView>(R.id.activity_edit_game__save_button).setOnClickListener {
            // TODO(save Materials)
            setResult(RESULT_OK, Intent().apply {
                putExtra("Game", game)
            })
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_game__add_button).setOnClickListener {

        }

        findViewById<CardView>(R.id.activity_edit_game__edit_button).setOnClickListener {

        }

        lifecycleScope.launch {
            if (gameId >= 0)
                viewModel.getGame(gameId).collect {
                    game = it
                }
            else
                viewModel.getCountGames().collect {
                    game = Game(it, "Title", "Description", null)
                }

            editGameTitle.text = game.name
            editGameDescription.text = game.description
            Glide.with(this@GameEditActivity)
                .load(game.image)
                .into(editGameImage)
        }
    }
    /*private fun drawDB(gameId:Int, materials:ArrayList<Model>) {
         val idMaterial = intent.getIntExtra("idMaterial", -1)
         when (intent.getIntExtra("typeMaterial", -1)) {
             4L -> {
                 gameDBViewModel.getOneDiceOfGame(gameId,idMaterial).observe(this) {dice ->
                     differentMaterialsadapter.setItem(dice)
                 }
             }
             5L ->{
                 gameDBViewModel.getOneNoteOfGame(gameId,idMaterial).observe(this) {note ->
                     differentMaterialsadapter.setItem(note)
                 }
             }
             6L->{
                 gameDBViewModel.getOneTimerOfGame(gameId,idMaterial).observe(this) {timer ->
                     differentMaterialsadapter.setItem(timer)
                 }
             }
             else -> {}
         }
     }*/

    private fun deleteMaterials(gameCount: Int, gameId: Int) {
        lifecycleScope.launch() {
            val game = viewModel.getGame(gameId).first()
            viewModel.updateGame(game)
        }
    }

    private fun fillRecycler(
        gameId: Int,
        materials: ArrayList<EntityROOM>,
        differentMaterialsadapter: MaterialsAdapter
    ) {
        lifecycleScope.launch() {
            var m: List<EntityROOM> = viewModel.getAllTimerOfGame(gameId).first()
            materials.addAll(m)
            m = viewModel.getAllDiceOfGame(gameId).first()
            materials.addAll(m)
            m = viewModel.getAllNoteOfGame(gameId).first()
            materials.addAll(m)
            m = viewModel.getAllHourglassOfGame(gameId).first()
            materials.addAll(m)

//            materials.sortByDescending { it.positionAdd }
            differentMaterialsadapter.setItems(materials)
        }

    }

}