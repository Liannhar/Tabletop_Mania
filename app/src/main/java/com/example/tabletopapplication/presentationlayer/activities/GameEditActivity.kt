package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.model.ModelLoader
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.example.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.example.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.example.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.GameEditViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.TimerDBViewModel

class GameEditActivity : AppCompatActivity(R.layout.activity_edit_game) {

    private val differentMaterialsadapter = ModelAdapter()
    private val noteViewModel by lazy{ ViewModelProvider(this)[NoteViewModel::class.java]}
    private val diceViewModel by lazy{ ViewModelProvider(this)[DiceDBViewModel::class.java]}
    private val timerViewModel by lazy{ ViewModelProvider(this)[TimerDBViewModel::class.java]}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<RecyclerView>(R.id.activity_edit_game__rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = differentMaterialsadapter
        }



        fillRecycler()

        val addButton = findViewById<ImageView>(R.id.activity_edit_game__add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, ChooseMaterialActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fillRecycler() {
        noteViewModel.getAllNotes().observe(this) {note ->
            differentMaterialsadapter.setItems(note)
        }
        diceViewModel.getAllDice().observe(this) {dice ->
            differentMaterialsadapter.setItems(dice)
        }
        timerViewModel.getAllTimer().observe(this) {timer ->
            differentMaterialsadapter.setItems(timer)
        }
    }

    override fun onResume() {
        super.onResume()

        when (intent.getIntExtra("idMaterial", -1)) {
            1 -> {
                noteViewModel.getAllNotes().observe(this) {note ->
                    differentMaterialsadapter.setItems(note)
                }
            }
            2 ->{

                diceViewModel.getAllDice().observe(this) {dice ->
                    differentMaterialsadapter.setItems(dice)
                }
            }
            3->{
                timerViewModel.getAllTimer().observe(this) {timer ->
                    differentMaterialsadapter.setItems(timer)
                }
            }
            else -> {}
        }
    }
}