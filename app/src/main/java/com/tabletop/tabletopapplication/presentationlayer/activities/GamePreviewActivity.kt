package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.tabletop.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.TimerDBViewModel

class GamePreviewActivity : AppCompatActivity(R.layout.activity_preview_game) {


    private val gameDBViewModel by lazy{ ViewModelProvider(this)[GameDBViewModel::class.java]}



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)

        val noteViewModel by lazy{ ViewModelProvider(this)[NoteViewModel::class.java]}
        val diceViewModel by lazy{ ViewModelProvider(this)[DiceDBViewModel::class.java]}
        val timerViewModel by lazy{ ViewModelProvider(this)[TimerDBViewModel::class.java]}
        val differentMaterialsadapter by lazy{ ModelAdapter(this,noteViewModel,diceViewModel,timerViewModel) }

        val previewGameTitle = findViewById<TextView>(R.id.activity_preview_game__title)
        val previewGameImage = findViewById<ImageView>(R.id.activity_preview_game__image)
        val previewGameDescription = findViewById<TextView>(R.id.activity_preview_game__description)

        gameDBViewModel.getGame(gameId).observe(this){game ->
            previewGameTitle.text = game.name
            previewGameDescription.text = game.description
            Glide.with(this).load(Uri.parse(game.image)).into(previewGameImage)
        }




        fillRecycler(gameId,differentMaterialsadapter)
        findViewById<RecyclerView>(R.id.activity_preview_game__rv).apply {
            adapter = differentMaterialsadapter
            layoutManager = LinearLayoutManager(context)
        }
        findViewById<ImageView>(R.id.activity_preview_game__back_button).setOnClickListener {
            val intent = Intent(this, GameSelectionActivity::class.java)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.PREVIEW.value)
        }
        findViewById<ImageView>(R.id.activity_preview_game__edit_button).setOnClickListener {
            val intent = Intent(this, GameEditActivity::class.java)
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.EDIT.value)
        }

    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fillRecycler()
    }*/


    private fun fillRecycler(gameId:Long,differentMaterialsadapter:ModelAdapter) {
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

