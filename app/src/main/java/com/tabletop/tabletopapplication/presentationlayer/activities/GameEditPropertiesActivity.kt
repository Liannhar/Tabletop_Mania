package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream


class GameEditPropertiesActivity : AppCompatActivity(R.layout.activity_edit_properties_game) {

    private var currentGame = Game()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var us=""
        currentGame = intent.extras?.run {
            getSerializable("Game") as Game
        } ?: Game()

        val nameView = findViewById<EditText>(R.id.activity_edit_properties_game__name)
        val descriptionView = findViewById<EditText>(R.id.activity_edit_properties_game__description)
        val imageView = findViewById<ImageView>(R.id.activity_edit_properties_game__image)

        /*val selectImageActivityLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->

            currentGame.image = uri.toString()

            Glide.with(imageView)
                .load(currentGame.image)
                .centerCrop()
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_error_outline_24)
                .into(imageView)
        }*/
        val selectImageActivityLauncher= registerForActivityResult(ActivityResultContracts.GetContent())
        { uri ->
            deleteToInternalStorage(us)
            val bitmap= BitmapFactory.decodeStream(contentResolver.openInputStream(uri!!))
            us = saveToInternalStorage(this,bitmap,getRandomString())
            Glide.with(imageView)
                .load(us)
                .centerCrop()
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_error_outline_24)
                .into(imageView)
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
            currentGame.image=us
            setResult(RESULT_OK, Intent().apply {
                putExtra("Game", currentGame)
            })
            finish()
        }

        lifecycleScope.launch {
            nameView.text = SpannableStringBuilder(currentGame.name)
            descriptionView.text = SpannableStringBuilder(currentGame.description)
            currentGame.image?.let{us=it}
            Glide.with(imageView)
                .load(currentGame.image)
                .centerCrop()
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_error_outline_24)
                .into(imageView)
        }
    }
    private fun saveToInternalStorage(context: Context, bitmapImage: Bitmap, imageName: String): String {
        val directory = context.getDir("images", Context.MODE_PRIVATE)
        val imagePath = File(directory, "$imageName.jpg")
        FileOutputStream(imagePath).use { fos ->
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        }
        return imagePath.absolutePath
    }

    private fun deleteToInternalStorage(imagePath:String){
        val file = File(imagePath)
        if (file.exists()) {
            file.delete()
        }
    }
    private fun getRandomString() : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..32)
            .map { allowedChars.random() }
            .joinToString("")
    }
}