package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.example.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.example.tabletopapplication.presentationlayer.models.DIce.Dice
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.models.Timer.Timer
import com.example.tabletopapplication.presentationlayer.viewmodels.*

class GameEditActivity : AppCompatActivity(R.layout.activity_edit_game) {

    private val gameId by lazy{ intent.getLongExtra("gameId",-1) }
    private val differentMaterialsadapter by lazy{ ModelAdapter(this, gameId)}
    private val gameDBViewModel by lazy{ ViewModelProvider(this)[GameDBViewModel::class.java]}
    private val noteViewModel by lazy{ ViewModelProvider(this)[NoteViewModel::class.java]}
    private val diceViewModel by lazy{ ViewModelProvider(this)[DiceDBViewModel::class.java]}
    private val timerViewModel by lazy{ ViewModelProvider(this)[TimerDBViewModel::class.java]}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        fillRecycler(gameId)
        findViewById<RecyclerView>(R.id.activity_edit_game__rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = differentMaterialsadapter
        }

        findViewById<ImageView>(R.id.activity_edit_game__save_button).setOnClickListener {
            val intent = Intent(this, GamePreviewActivity::class.java)
            intent.putExtra("gameId",gameId)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.PREVIEW.value)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_game__back_button).setOnClickListener {
            val intent = Intent(this, GamePreviewActivity::class.java)
            intent.putExtra("gameId",gameId)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.PREVIEW.value)
        }

        val addButton = findViewById<ImageView>(R.id.activity_edit_game__add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, ChooseMaterialActivity::class.java)
            intent.putExtra("gameId",gameId)
            startActivity(intent)
        }

        findViewById<CardView>(R.id.activity_edit_game__edit_button).setOnClickListener {
            val intent = Intent(this, GameEditPropertiesActivity::class.java)
            intent.putExtra("gameId",gameId)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.PREVIEW.value)
        }




    }
    fun drawDB() {
        val idMaterial = intent.getLongExtra("idMaterial", -1)
        val typeMaterial = intent.getLongExtra("typeMaterial", -1)
        when (typeMaterial) {
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
    }

    private fun fillRecycler(gameId:Long) {
        gameDBViewModel.getAllNoteOfGame(gameId).observe(this) {note ->

            differentMaterialsadapter.setItems(note)
        }
        gameDBViewModel.getAllDiceOfGame(gameId).observe(this) {dice ->

            differentMaterialsadapter.setItems(dice)
        }
        gameDBViewModel.getAllTimerOfGame(gameId).observe(this) {timer ->

            differentMaterialsadapter.setItems(timer)
        }
    }

}