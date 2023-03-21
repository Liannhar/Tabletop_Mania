package com.example.tabletopapplication.editPropertiesGame

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tabletopapplication.R
import com.example.tabletopapplication.common.Game

class EditPropertiesGameActivity : AppCompatActivity(R.layout.activity_edit_properties_game) {

    private lateinit var game: Game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arguments = intent.extras
        if (arguments != null)
            game = arguments.getSerializable("Game") as Game

        // Test data
        game = Game("Test", "Test Test Test Test Test Test Test Test ", R.drawable.cubes)

        setGameProperties(game)
    }

    private fun setGameProperties(game: Game) {
        findViewById<ImageView>(R.id.activity_edit_properties_game__image).apply {
            setImageResource(game.image)
        }
        findViewById<TextView>(R.id.activity_edit_properties_game__title).apply {
            text = game.title
        }
        findViewById<TextView>(R.id.activity_edit_properties_game__description).apply {
            text = game.description
        }
    }
}