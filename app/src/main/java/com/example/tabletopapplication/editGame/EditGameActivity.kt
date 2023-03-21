package com.example.tabletopapplication.editGame

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.common.Game
import com.example.tabletopapplication.common.Material
import com.example.tabletopapplication.common.adapters.CMRecyclerAdapter

class EditGameActivity : AppCompatActivity(R.layout.activity_edit_game) {

    private lateinit var game: Game
    private lateinit var CMRadapter: CMRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val arguments = intent.extras
        if (arguments != null)
            game = arguments.getSerializable("Game") as Game

        // Test data
        game = Game(
            "Test", "Test Test Test Test Test Test Test Test Test", R.drawable.cubes,
            arrayListOf(
                Material("Material1", "Description1", R.drawable.cubes),
                Material("Material2", "Description2", R.drawable.cubes),
                Material("Material3", "Description3", R.drawable.cubes),
                Material("Material4", "Description4", R.drawable.cubes),
                Material("Material5", "Description5", R.drawable.cubes),
                Material("Material6", "Description6", R.drawable.cubes),
            )
        )

        CMRadapter = CMRecyclerAdapter(editMode = true)
        findViewById<RecyclerView>(R.id.activity_edit_game__rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CMRadapter
        }

        setGameProperties(game)
    }

    private fun setGameProperties(game: Game) {
        findViewById<ImageView>(R.id.activity_edit_game__image).apply {
            setImageResource(game.image)
        }
        findViewById<TextView>(R.id.activity_edit_game__title).apply {
            text = game.title
        }
        findViewById<TextView>(R.id.activity_edit_game__description).apply {
            text = game.description
        }

        CMRadapter.addListMaterials(game.materials)
    }
}