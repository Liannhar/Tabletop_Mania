package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.businesslayer.models.MaterialEntity
import com.example.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.example.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.example.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.example.tabletopapplication.presentationlayer.models.DIce.Dice
import com.example.tabletopapplication.presentationlayer.models.LoadState
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.models.Timer.Timer
import com.example.tabletopapplication.presentationlayer.viewmodels.*

class GamePreviewActivity : AppCompatActivity(R.layout.activity_preview_game) {

    private val viewModel = GamePreviewViewModel()
    private val gameId by lazy{ intent.getLongExtra("gameId",-1) }
    private val differentMaterialsadapter by lazy{ ModelAdapter(this, gameId)}
    private val gameDBViewModel by lazy{ ViewModelProvider(this)[GameDBViewModel::class.java]}
    private val noteViewModel by lazy{ ViewModelProvider(this)[NoteViewModel::class.java]}
    private val diceViewModel by lazy{ ViewModelProvider(this)[DiceDBViewModel::class.java]}
    private val timerViewModel by lazy{ ViewModelProvider(this)[TimerDBViewModel::class.java]}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        fillRecycler(gameId)
        findViewById<RecyclerView>(R.id.activity_preview_game__rv).apply {
            adapter = differentMaterialsadapter
            layoutManager = LinearLayoutManager(context)
        }
        findViewById<ImageView>(R.id.activity_preview_game__back_button).setOnClickListener {
            val intent = Intent(this, GameSelectionActivity::class.java).apply {
                putExtra("gameId",gameId) }

            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.PREVIEW.value)
        }
        findViewById<ImageView>(R.id.activity_preview_game__edit_button).setOnClickListener {
            val intent = Intent(this, GameEditActivity::class.java).apply {
                //putExtra("Game", viewModel.game)
                putExtra("gameId",gameId)
            }
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.EDIT.value)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fillRecycler(intent.getLongExtra("gameId",-1))
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