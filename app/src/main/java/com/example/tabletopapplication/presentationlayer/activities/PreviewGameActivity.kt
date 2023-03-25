package com.example.tabletopapplication.presentationlayer.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.models.Game
import com.example.tabletopapplication.businesslayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.example.tabletopapplication.presentationlayer.adapters.ModelAdapter


class PreviewGameActivity : AppCompatActivity(R.layout.activity_preview_game) {

    private lateinit var game: Game
    private val materialAdapter = ModelAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arguments = intent.extras
        if (arguments != null)
            game = arguments.getSerializable("Game") as Game

        // Test data
        game = Game(
            "Test", "Test Test Test Test Test Test Test Test Test", R.drawable.cubes,
            arrayListOf(
                Material("Material1", "Description1", "A"),
                Material("Material2", "Description2", "A"),
                Material("Material3", "Description3", "A"),
                Material("Material4", "Description4", "A"),
                Material("Material5", "Description5", "A"),
                Material("Material6", "Description6", "A"),
            )
        )

        materialAdapter.setItems(game.materials)

        findViewById<RecyclerView>(R.id.activity_preview_game__rv).apply {
            adapter = materialAdapter
            layoutManager = LinearLayoutManager(context)
        }

        setGameProperties(game)
    }

    private fun setGameProperties(game: Game) {
        findViewById<ImageView>(R.id.activity_preview_game__image).apply {
            setImageResource(game.image)
        }
        findViewById<TextView>(R.id.activity_preview_game__title).apply {
            text = game.title
        }
        findViewById<TextView>(R.id.activity_preview_game__description).apply {
            text = game.description
        }

        //materialAdapter.addListMaterials(game.materials)
    }
}