package com.tabletop.tabletopapplication.presentationlayer.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class GameEditPropertiesActivity : AppCompatActivity(R.layout.activity_edit_properties_game) {

    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[GameDBViewModel::class.java]
    }

    private var currentGame = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentGame = intent.extras?.run {
            getParcelable("Game") ?: Game()
        } ?: Game()

        val nameView = findViewById<EditText>(R.id.activity_edit_properties_game__name)
        val descriptionView = findViewById<EditText>(R.id.activity_edit_properties_game__description)
        val imageView = findViewById<ImageView>(R.id.activity_edit_properties_game__image)

        lifecycle.coroutineScope.launch {
            nameView.text = SpannableStringBuilder(currentGame.name)
            descriptionView.text = SpannableStringBuilder(currentGame.description)
            Glide.with(applicationContext)
                .load(currentGame.image)
                .error(R.drawable.baseline_error_outline_24)
                .into(imageView)
        }

        findViewById<ImageView>(R.id.activity_edit_properties_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_properties_game__save_button).setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("Game", currentGame)
            })
            finish()
        }

        val selectImageActivityLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageView.setImageURI(uri)
            currentGame.image = uri.toString()
        }

        findViewById<CardView>(R.id.activity_edit_properties_game__select_file).setOnClickListener {
            selectImageActivityLauncher.launch("image/*")
        }

        findViewById<ImageView>(R.id.activity_edit_properties_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_properties_game__save_button).setOnClickListener {
            currentGame.name = nameView.text.toString()
            currentGame.description = descriptionView.text.toString()

            setResult(RESULT_OK, Intent().apply {
                putExtra("Game", currentGame)
            })
            finish()
        }

    }
}
