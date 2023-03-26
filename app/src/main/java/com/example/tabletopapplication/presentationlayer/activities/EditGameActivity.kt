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
import com.example.tabletopapplication.presentationlayer.models.Timer.Timer
import com.example.tabletopapplication.presentationlayer.viewmodels.NoteViewModel

class EditGameActivity : AppCompatActivity(R.layout.activity_edit_game) {

    private lateinit var game: Game
    private val differentMaterialsadapter = ModelAdapter()
    private val noteViewModel by lazy{ViewModelProvider(this)[NoteViewModel::class.java]}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val arguments = intent.extras
        if (arguments != null)
            game = arguments.getSerializable("Game") as Game

        // Test data
        game = Game(
            "Test", "Test Test Test Test Test Test Test Test Test", R.drawable.cubes, arrayListOf(
                Note("")
            )
        )*/
        findViewById<RecyclerView>(R.id.activity_edit_game__rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = differentMaterialsadapter
        }

        //setGameProperties(game)

        val addButton = findViewById<ImageView>(R.id.activity_edit_game__add_button)

        addButton.setOnClickListener {
            val intent = Intent(this, ChooseMaterialActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val id = intent.getIntExtra("idMaterial", -1)
        when (intent.getIntExtra("typoMaterial", -1)) {
            1 -> {
                val noteObserver = Observer<Note> { data ->

                    differentMaterialsadapter.setItem(data)
                }
                noteViewModel.getNote(id).observe(this,noteObserver)
            }
            2->{
                differentMaterialsadapter.setItem(Timer())
            }
            3->{
                differentMaterialsadapter.setItem(Dice())
            }
            else -> {}
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