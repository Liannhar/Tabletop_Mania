package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.example.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.example.tabletopapplication.presentationlayer.viewmodels.GameEditViewModel

class GameEditActivity : AppCompatActivity(R.layout.activity_edit_game) {

    private val viewModel = GameEditViewModel()
    private lateinit var MRadapter: MaterialRecyclerAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize
        val arguments = intent.extras
        if (arguments != null)
            viewModel.game = arguments.getParcelable("Game", GameEntity::class.java)!!


        MRadapter = MaterialRecyclerAdapter()
        findViewById<RecyclerView>(R.id.activity_edit_game__rv).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MRadapter
        }

        // Clicks
        findViewById<ImageView>(R.id.activity_edit_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_game__save_button).setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("Game", viewModel.game)
            })
            finish()
        }

        findViewById<CardView>(R.id.activity_edit_game__edit_button).setOnClickListener {
            val intent = Intent(this, GameEditPropertiesActivity::class.java).apply {
                putExtra("Game", viewModel.game)
            }
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.EDIT_PROPERTIES.value)
        }

        viewModel.LDgame.observe(this) {
            findViewById<TextView>(R.id.activity_edit_game__title).apply {
                text = viewModel.game?.name ?: "Name"
            }
            findViewById<TextView>(R.id.activity_edit_game__description).apply {
                text = viewModel.game?.description ?: "Description"
            }
            // TODO Добавить заполнение materials and image
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            ACTIVITY_REQUEST_CODE.EDIT_PROPERTIES.value -> {
                when(resultCode) {
                    RESULT_OK -> {
                        viewModel.game = data?.extras?.getParcelable("Game")
                    }
                }
            }
        }
    }
}