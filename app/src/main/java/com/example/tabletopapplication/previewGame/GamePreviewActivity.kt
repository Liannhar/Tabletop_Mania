package com.example.tabletopapplication.previewGame

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

class GamePreviewActivity: AppCompatActivity(R.layout.activity_preview_game) {

    private lateinit var game: Game
    private lateinit var CMRadapter: CMRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Test data
        game = Game("Test", "Test Test Test Test Test Test Test Test Test", R.drawable.cubes,
            arrayListOf(
                Material("Material1", "Description1", R.drawable.cubes),
                Material("Material2", "Description2", R.drawable.cubes),
                Material("Material3", "Description3", R.drawable.cubes),
                Material("Material4", "Description4", R.drawable.cubes),
                Material("Material5", "Description5", R.drawable.cubes),
                Material("Material6", "Description6", R.drawable.cubes),
            )
        )

        CMRadapter = CMRecyclerAdapter()
        findViewById<RecyclerView>(R.id.activity_preview_game_rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = CMRadapter
        }

        setGameProperties(game)
    }

    private fun setGameProperties(game: Game) {
        findViewById<ImageView>(R.id.activity_preview_game_image).apply {
            setImageResource(game.image)
        }
        findViewById<TextView>(R.id.activity_preview_game_title).apply {
            text = game.title
        }
        findViewById<TextView>(R.id.activity_preview_game_description).apply {
            text = game.description
        }

        CMRadapter.addListMaterials(game.materials)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putSerializable("Game", game)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        game = savedInstanceState.getSerializable("Game") as Game
    }
}