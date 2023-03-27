package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.businesslayer.models.MaterialEntity
import com.example.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.example.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.example.tabletopapplication.presentationlayer.models.LoadState
import com.example.tabletopapplication.presentationlayer.viewmodels.GamePreviewViewModel

class GamePreviewActivity : AppCompatActivity(R.layout.activity_preview_game) {

    private val viewModel = GamePreviewViewModel()

    private lateinit var MRadapter: MaterialRecyclerAdapter

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize
        val arguments = intent.extras
        if (arguments != null)
            viewModel.game = arguments.getParcelable("Game", GameEntity::class.java)!!


        MRadapter = MaterialRecyclerAdapter()
        findViewById<RecyclerView>(R.id.activity_preview_game__rv).apply {
            adapter = MRadapter
            layoutManager = LinearLayoutManager(context)
        }

        // Clicks
        findViewById<ImageView>(R.id.activity_preview_game__back_button).setOnClickListener {
            if (haveChanges) {
                setResult(RESULT_OK, Intent().apply {
                    putExtra("Game", viewModel.game)
                })
            } else {
                setResult(RESULT_CANCELED)
            }
            finish()
        }

        findViewById<ImageView>(R.id.activity_preview_game__edit_button).setOnClickListener {
            val intent = Intent(this, GameEditActivity::class.java).apply {
                putExtra("Game", viewModel.game)
            }
            startActivityForResult(intent, ACTIVITY_REQUEST_CODE.EDIT.value)
        }


        // ViewModel observes
        viewModel.LDgame.observe(this) { game ->
            findViewById<TextView>(R.id.activity_preview_game__title).apply {
                text = game.name
            }
            findViewById<TextView>(R.id.activity_preview_game__description).apply {
                text = game.description
            }

            // TODO Добавить заполнение materials and image
        }

        viewModel.LDstate.observe(this) { state ->
            when (state) {
                is LoadState.Initialized -> Unit
                is LoadState.Pending -> Unit
                is LoadState.Success<*> ->
                    when(state.result) {
                        is MaterialEntity -> MRadapter.addMaterial(state.result)
                        is ArrayList<*> -> MRadapter.addMaterials(state.result as List<MaterialEntity>)
                    }
                is LoadState.Error -> Unit
            }
        }

        viewModel.load()
    }

    private var haveChanges = false

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            ACTIVITY_REQUEST_CODE.EDIT.value -> {
                when(resultCode) {
                    RESULT_OK -> {
                        viewModel.game = data?.extras?.getParcelable("Game")
                        haveChanges = true
                    }
                }
            }
        }
    }
}