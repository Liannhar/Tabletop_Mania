package com.tabletop.tabletopapplication.presentationlayer.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel

class GameEditPropertiesActivity : AppCompatActivity(R.layout.activity_edit_properties_game) {

    private val viewModel by lazy{ ViewModelProvider(this)[GameDBViewModel::class.java]}
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)
        //val game =
        // Initialize
       /* val arguments = intent.extras
        if (arguments != null)
            viewModel.game = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments.getParcelable("Game", GameEntity::class.java)
            } else {
                intent.getParcelableExtra("Game")
            }*/
        viewModel.getGame(gameId).observe(this) {game->
            game.name= findViewById<EditText>(R.id.activity_edit_properties_game__name).text.toString()
            viewModel.updateGame(game)
        }
        // Clicks
        findViewById<ImageView>(R.id.activity_edit_properties_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            val intent = Intent(this, GameEditActivity::class.java)
            startActivity(intent)
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
                game.description= findViewById<EditText>(R.id.activity_edit_properties_game__description).text.toString()
                viewModel.updateGame(game)
            }
            viewModel.getGame(gameId).observe(this) {game->
                game.image= findViewById<ImageView>(R.id.activity_edit_properties_game__image).toString()
                viewModel.updateGame(game)
            }
            val intent = Intent(this, GameEditActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Observers
        viewModel.getGame(gameId).observe(this) { game ->
            findViewById<TextView>(R.id.activity_edit_properties_game__name).apply {
                text = game.name
            }
            findViewById<TextView>(R.id.activity_edit_properties_game__description).apply {
                text = game.description
            }
            findViewById<ImageView>(R.id.activity_edit_properties_game__image).apply {
                setImageURI(Uri.parse(game.image))
            }
        }
        val selectImageIntent = registerForActivityResult(ActivityResultContracts.GetContent())
        { uri ->
            findViewById<ImageView>(R.id.activity_edit_properties_game__image).apply {setImageURI(uri) }

        }
        findViewById<CardView>(R.id.activity_edit_properties_game__select_file).setOnClickListener {
            selectImageIntent.launch("image/*")
        }
    }
}
