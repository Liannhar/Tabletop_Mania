package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialsAdapter
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.EntityROOM
import com.tabletop.tabletopapplication.presentationlayer.contracts.IntentGameContract
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class GamePreviewActivity : AppCompatActivity(R.layout.activity_preview_game) {


    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[GameDBViewModel::class.java]
    }

    private var currentGame = Game()
    private val differentMaterialsAdapter by lazy{ MaterialsAdapter(this, databaseVM) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        currentGame.id = (intent.extras?.run {
            getInt("id", -1)
        } ?: -1)

        val previewGameTitle = findViewById<TextView>(R.id.activity_preview_game__title)
        val previewGameImage = findViewById<ImageView>(R.id.activity_preview_game__image)
        val previewGameDescription = findViewById<TextView>(R.id.activity_preview_game__description)

        lifecycleScope.launch {
            databaseVM.getGame(currentGame.id)?.let {
                currentGame = it
            }

            previewGameTitle.text = currentGame.name
            previewGameDescription.text = currentGame.description

            Glide.with(this@GamePreviewActivity)
                .load(currentGame.image)
                .into(previewGameImage)
        }

        findViewById<RecyclerView>(R.id.activity_preview_game__rv).apply {
            adapter = differentMaterialsAdapter
            layoutManager = LinearLayoutManager(context)
        }

        findViewById<ImageView>(R.id.activity_preview_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        val editActivityLauncher = registerForActivityResult(IntentGameContract()) { result ->
            result?.let {

                currentGame = result

                Log.i("ASD", currentGame.id.toString())
                Log.i("ASD", currentGame.name.toString())
                Log.i("ASD", currentGame.description.toString())
                Log.i("ASD", currentGame.image.toString())

                previewGameTitle.text = currentGame.name
                previewGameDescription.text = currentGame.description

                Glide.with(this@GamePreviewActivity)
                    .load(currentGame.image)
                    .into(previewGameImage)
            }
        }

        findViewById<ImageView>(R.id.activity_preview_game__edit_button).setOnClickListener {
            editActivityLauncher.launch(Intent(applicationContext, GameEditActivity::class.java).apply {
                putExtra("id", currentGame.id)
            })
        }

    }

    private fun СheckMaterials(gameId: Int,job:Job){
//        lifecycleScope.launch(){
//            job.cancel()
//            val game = gameDBViewModel.getGame(gameId).first()
//            if (game.count==0)
//            {
//
//                gameDBViewModel.deleteGame(game)
//            }
//
//        }
    }


    private fun fillRecycler(gameId:Int, differentMaterialsadapter:MaterialsAdapter, materials:ArrayList<EntityROOM>) {
//        lifecycleScope.launch(){
//            var m:List<EntityROOM> =   gameDBViewModel.getAllTimerOfGame(gameId).first()
//            materials.addAll(m)
//            m =   gameDBViewModel.getAllDiceOfGame(gameId).first()
//            materials.addAll(m)
//            m =   gameDBViewModel.getAllNoteOfGame(gameId).first()
//            materials.addAll(m)
//            m =   gameDBViewModel.getAllHourglassOfGame(gameId).first()
//            materials.addAll(m)
//
//            //materials.sortByDescending { it.positionAdd }
//            differentMaterialsadapter.updateItems(materials)
//        }
        /*gameDBViewModel.getAllTimerOfGame(gameId).onEach { newList ->
            materials.addAll(newList)
        }.launchIn(lifecycleScope)
        gameDBViewModel.getAllNoteOfGame(gameId).onEach { newList ->
            materials.addAll(newList)
        }.launchIn(lifecycleScope)
        gameDBViewModel.getAllHourglassOfGame(gameId).onEach { newList ->
            materials.addAll(newList)
        }.launchIn(lifecycleScope)
        gameDBViewModel.getAllDiceOfGame(gameId).onEach { newList ->
            materials.addAll(newList)
        }.launchIn(lifecycleScope)
        materials.sortByDescending { it.positionAdd }
        differentMaterialsadapter.updateItems(materials)*/
    }
}

