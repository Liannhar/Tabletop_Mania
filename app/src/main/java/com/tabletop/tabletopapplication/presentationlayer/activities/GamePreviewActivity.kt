package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.tabletop.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.models.Note.Note
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GamePreviewActivity : AppCompatActivity(R.layout.activity_preview_game) {


    private val gameDBViewModel by lazy{ ViewModelProvider(this)[GameDBViewModel::class.java]}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)
        prefs.edit().putLong("currentGameId", gameId).apply()
        val differentMaterialsadapter by lazy{ ModelAdapter(this,gameDBViewModel) }
        val materials = arrayListOf<Model>()
        val previewGameTitle = findViewById<TextView>(R.id.activity_preview_game__title)
        val previewGameImage = findViewById<ImageView>(R.id.activity_preview_game__image)
        val previewGameDescription = findViewById<TextView>(R.id.activity_preview_game__description)

        val job = lifecycleScope.launch(){
                gameDBViewModel.getGame(gameId).collect(){
                        previewGameTitle.text = it.name
                        previewGameDescription.text = it.description
                        Glide.with(this@GamePreviewActivity).load(it.image).into(previewGameImage)
                }

        }

        /*gameDBViewModel.getGame(gameId).observe(this){game ->
            previewGameTitle.text = game.name
            previewGameDescription.text = game.description
            Glide.with(this).load(game.image).into(previewGameImage)
        }*/




        fillRecycler(gameId,differentMaterialsadapter,materials)
        findViewById<RecyclerView>(R.id.activity_preview_game__rv).apply {
            adapter = differentMaterialsadapter
            layoutManager = LinearLayoutManager(context)
        }
        findViewById<ImageView>(R.id.activity_preview_game__back_button).setOnClickListener {
            СheckMaterials(gameId,job)
            val intent = Intent(this, GameSelectionActivity::class.java)
            startActivity(intent)
        }
        findViewById<ImageView>(R.id.activity_preview_game__edit_button).setOnClickListener {
            val intent = Intent(this, GameEditActivity::class.java)
            startActivity(intent)
        }

    }

    private fun СheckMaterials(gameId: Long,job:Job){
        lifecycleScope.launch(){
            job.cancel()
            val game = gameDBViewModel.getGame(gameId).first()
            if (game.count==0)
            {

                gameDBViewModel.deleteGame(game)
            }

        }
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fillRecycler()
    }*/


    private fun fillRecycler(gameId:Long,differentMaterialsadapter:ModelAdapter,materials:ArrayList<Model>) {
        lifecycleScope.launch(){
            var m:List<Model> =   gameDBViewModel.getAllTimerOfGame(gameId).first()
            materials.addAll(m)
            m =   gameDBViewModel.getAllDiceOfGame(gameId).first()
            materials.addAll(m)
            m =   gameDBViewModel.getAllNoteOfGame(gameId).first()
            materials.addAll(m)
            m =   gameDBViewModel.getAllHourglassOfGame(gameId).first()
            materials.addAll(m)

            materials.sortByDescending { it.positionAdd }
            differentMaterialsadapter.updateItems(materials)
        }
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

