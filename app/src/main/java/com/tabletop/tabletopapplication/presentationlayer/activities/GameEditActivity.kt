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
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.presentationlayer.contracts.IntentGameContract
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class GameEditActivity : AppCompatActivity(R.layout.activity_edit_game) {

    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[GameDBViewModel::class.java]
    }

    private var currentGame: Game = Game()
    private val differentMaterialsAdapter by lazy { MaterialsAdapter(this, databaseVM) }

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
            adapter = differentMaterialsAdapter
        }

        findViewById<ImageView>(R.id.activity_edit_game__save_button).setOnClickListener {
            // TODO(save Materials)
            lifecycleScope.launch {
                databaseVM.addGame(currentGame)
            }

            setResult(RESULT_OK, Intent().apply {
                putExtra("Game", currentGame)
            })
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_game__add_button).setOnClickListener {

        }

        val editGameActivityLauncher = registerForActivityResult(IntentGameContract()) { result ->
            lifecycleScope.launch {
                result?.let {
                    currentGame = it

                    editGameTitle.text = currentGame.name
                    editGameDescription.text = currentGame.description

                    Glide.with(this@GameEditActivity)
                        .load(currentGame.image)
                        .error(R.drawable.baseline_error_outline_24)
                        .into(editGameImage)
                }
            }
        }

        findViewById<CardView>(R.id.activity_edit_game__edit_button).setOnClickListener {
            editGameActivityLauncher.launch(Intent(applicationContext, GameEditPropertiesActivity::class.java).apply {
                putExtra("Game", currentGame)
            })
        }

        lifecycleScope.launch {

            databaseVM.getGame(currentGame.id)?.run {
                currentGame = this
            } ?: launch {
                currentGame = Game(databaseVM.getCountGames(), "Title", "Description", null)
            }.join()

            editGameTitle.text = currentGame.name
            editGameDescription.text = currentGame.description
            Glide.with(this@GameEditActivity)
                .load(currentGame.image)
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

//    private fun deleteMaterials(gameCount: Int, gameId: Int) {
//        lifecycleScope.launch {
//            val game = viewModel.getGame(gameId)
//            viewModel.updateGame(game)
//        }
//    }

/*    private fun fillRecycler(
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

    }*/

}