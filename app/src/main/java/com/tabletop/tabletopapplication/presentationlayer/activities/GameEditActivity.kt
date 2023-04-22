package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.tabletop.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.TimerDBViewModel

class GameEditActivity : AppCompatActivity(R.layout.activity_edit_game) {



    private val gameDBViewModel by lazy{ ViewModelProvider(this)[GameDBViewModel::class.java]}
    private val materials = arrayListOf<Model>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)

        val editGameTitle = findViewById<TextView>(R.id.activity_edit_game__title)
        val editGameImage = findViewById<ImageView>(R.id.activity_edit_game__image)
        val editGameDescription = findViewById<TextView>(R.id.activity_edit_game__description)

        val noteViewModel by lazy{ ViewModelProvider(this)[NoteViewModel::class.java]}
        val diceViewModel by lazy{ ViewModelProvider(this)[DiceDBViewModel::class.java]}
        val timerViewModel by lazy{ ViewModelProvider(this)[TimerDBViewModel::class.java]}
        val differentMaterialsadapter by lazy{ ModelAdapter(this,noteViewModel,diceViewModel,timerViewModel)}


        gameDBViewModel.getGame(gameId).observe(this){game ->
            editGameTitle.text = game.name
            editGameDescription.text = game.description
            Glide.with(this).load(game.image).into(editGameImage)
        }

        fillRecycler(gameId,materials,differentMaterialsadapter)
        //drawDB(gameId,materials)
        findViewById<RecyclerView>(R.id.activity_edit_game__rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = differentMaterialsadapter
        }

        findViewById<ImageView>(R.id.activity_edit_game__save_button).setOnClickListener {
            val intent = Intent(this, GamePreviewActivity::class.java)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.PREVIEW.value)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_game__back_button).setOnClickListener {
            val intent = Intent(this, GamePreviewActivity::class.java)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.PREVIEW.value)
        }

        val addButton = findViewById<ImageView>(R.id.activity_edit_game__add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, ChooseMaterialActivity::class.java)
            startActivity(intent)
        }

        findViewById<CardView>(R.id.activity_edit_game__edit_button).setOnClickListener {
            val intent = Intent(this, GameEditPropertiesActivity::class.java)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.PREVIEW.value)
        }

    }
   /*private fun drawDB(gameId:Long, materials:ArrayList<Model>) {
        val idMaterial = intent.getLongExtra("idMaterial", -1)
        when (intent.getLongExtra("typeMaterial", -1)) {
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



    private fun fillRecycler(gameId:Long,materials:ArrayList<Model>,differentMaterialsadapter:ModelAdapter) {

        gameDBViewModel.getAllNoteOfGame(gameId).observe(this) {note ->
            //note.forEach {materials.add(it) }
            differentMaterialsadapter.setItems(note)
            Log.i("AAAAAA","note")
        }
        gameDBViewModel.getAllDiceOfGame(gameId).observe(this) {dice ->
            //dice.forEach {materials.add(it) }
            differentMaterialsadapter.setItems(dice)
            Log.i("AAAAAA","dice")
        }
        gameDBViewModel.getAllTimerOfGame(gameId).observe(this) {timer ->
            //timer.forEach { materials.add(it) }
            differentMaterialsadapter.setItems(timer)
            Log.i("AAAAAA","timer")
        }
    }

}