package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
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
import com.example.tabletopapplication.presentationlayer.models.LoadState
import com.example.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.GamePreviewViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.TimerDBViewModel

class GamePreviewActivity : AppCompatActivity(R.layout.activity_preview_game) {


    private val noteViewModel by lazy{ ViewModelProvider(this)[NoteViewModel::class.java]}
    private val diceViewModel by lazy{ ViewModelProvider(this)[DiceDBViewModel::class.java]}
    private val timerViewModel by lazy{ ViewModelProvider(this)[TimerDBViewModel::class.java]}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val differentmaterialsadapter = ModelAdapter()

        fillRecycler(differentmaterialsadapter)


        findViewById<RecyclerView>(R.id.activity_preview_game__rv).apply {
            adapter = differentmaterialsadapter
            layoutManager = LinearLayoutManager(context)
        }



    }


    private fun fillRecycler(differentmaterialsadapter: ModelAdapter) {
        noteViewModel.getAllNotes().observe(this) {note ->
            differentmaterialsadapter.setItems(note)
        }
        diceViewModel.getAllDice().observe(this) {dice ->
            differentmaterialsadapter.setItems(dice)
        }
        timerViewModel.getAllTimer().observe(this) {timer ->
            differentmaterialsadapter.setItems(timer)
        }
    }
}