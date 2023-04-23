package com.example.tabletopapplication.presentationlayer.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.GameEditPropertiesViewModel

class GameEditPropertiesActivity : AppCompatActivity(R.layout.activity_edit_properties_game) {

    private val viewModel by lazy{ ViewModelProvider(this)[GameDBViewModel::class.java]}

    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)

        // Initialize
       /* val arguments = intent.extras
        if (arguments != null)
            viewModel.game = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments.getParcelable("Game", GameEntity::class.java)
            } else {
                intent.getParcelableExtra("Game")
            }*/

        // Clicks
        findViewById<ImageView>(R.id.activity_edit_properties_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_properties_game__save_button).setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
            })
            viewModel.getGame(gameId).observe(this) {game->
                game.name= findViewById<EditText>(R.id.activity_edit_properties_game__name).text.toString()
                viewModel.updateGame(game)
            }
            viewModel.getGame(gameId).observe(this) {game->
                Log.i("AAAAAAA",findViewById<EditText>(R.id.activity_edit_properties_game__description).text.toString())
                game.description= findViewById<EditText>(R.id.activity_edit_properties_game__description).text.toString()
                viewModel.updateGame(game)
            }

            finish()
        }


        // Changes
        /*findViewById<EditText>(R.id.activity_edit_properties_game__name).doAfterTextChanged {
        }

        findViewById<EditText>(R.id.activity_edit_properties_game__description).doAfterTextChanged {

        }*/



        // Observers
        viewModel.getGame(gameId).observe(this) { game ->
            findViewById<TextView>(R.id.activity_edit_properties_game__name).apply {
                text = game.name
            }
            findViewById<TextView>(R.id.activity_edit_properties_game__description).apply {
                text = game.description
            }
        }
    }
}