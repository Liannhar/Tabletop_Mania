package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.example.tabletopapplication.presentationlayer.models.Game
import com.example.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.example.tabletopapplication.presentationlayer.models.DIce.Dice
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.models.Model
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.TimerDBViewModel

class EditGameActivity : AppCompatActivity(R.layout.activity_edit_game) {

    private lateinit var game: Game
    private val differentMaterialsadapter = ModelAdapter()
    private val noteViewModel by lazy{ViewModelProvider(this)[NoteViewModel::class.java]}
    private val diceViewModel by lazy{ViewModelProvider(this)[DiceDBViewModel::class.java]}
    private val timerViewModel by lazy{ViewModelProvider(this)[TimerDBViewModel::class.java]}

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
            else -> {Log.i("AAAAAAAAAAAAAA","435")}
        }
    }



    /*fun setGameProperties(game: Game) {
        findViewById<ImageView>(R.id.activity_edit_game__image).apply {
            setImageResource(game.image)
        }
        findViewById<TextView>(R.id.activity_edit_game__title).apply {
            text = game.title
        }
        findViewById<TextView>(R.id.activity_edit_game__description).apply {
            text = game.description
        }

        //CMRadapter.addListMaterials(game.materials)
    }*/
}