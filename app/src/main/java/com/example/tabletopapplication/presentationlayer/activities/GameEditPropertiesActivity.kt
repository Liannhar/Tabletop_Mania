package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.presentationlayer.viewmodels.GameEditPropertiesViewModel

class GameEditPropertiesActivity : AppCompatActivity(R.layout.activity_edit_properties_game) {

    private val viewModel = GameEditPropertiesViewModel()

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize
        val arguments = intent.extras
        if (arguments != null)
            viewModel.game = arguments.getParcelable("Game", GameEntity::class.java)!!

        // Clicks
        findViewById<ImageView>(R.id.activity_edit_properties_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_properties_game__save_button).setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("Game", viewModel.game)
            })
            finish()
        }

        // Changes
        findViewById<EditText>(R.id.activity_edit_properties_game__name).doAfterTextChanged {
            viewModel.game?.name =
                findViewById<EditText>(R.id.activity_edit_properties_game__name).text.toString()
        }

        findViewById<EditText>(R.id.activity_edit_properties_game__description).doAfterTextChanged {
            viewModel.game?.description =
                findViewById<EditText>(R.id.activity_edit_properties_game__description).text.toString()
        }

        // Observers
        viewModel.LDgame.observe(this) { game ->
            findViewById<TextView>(R.id.activity_edit_properties_game__name).apply {
                text = game.name
            }
            findViewById<TextView>(R.id.activity_edit_properties_game__description).apply {
                text = game.description
            }
        }
    }
}