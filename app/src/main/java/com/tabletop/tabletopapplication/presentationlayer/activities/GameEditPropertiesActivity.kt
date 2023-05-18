package com.tabletop.tabletopapplication.presentationlayer.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
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
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream


class GameEditPropertiesActivity : AppCompatActivity(R.layout.activity_edit_properties_game) {

    private val viewModel by lazy{ ViewModelProvider(this)[GameDBViewModel::class.java]}
    @SuppressLint("CutPasteId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var us=""
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)
        // Initialize
       /* val arguments = intent.extras
        if (arguments != null)
            viewModel.game = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                arguments.getParcelable("Game", GameEntity::class.java)
            } else {
                intent.getParcelableExtra("Game")
            }*/
        // Clicks
        findViewById<ImageView>(R.id.activity_edit_properties_game__back_button).setOnClickListener {
            setResult(RESULT_CANCELED)
            val intent = Intent(this, GameEditActivity::class.java)
            startActivity(intent)
            finish()
        }

        findViewById<ImageView>(R.id.activity_edit_properties_game__save_button).setOnClickListener {
            setResult(RESULT_OK, Intent().apply {})
            lifecycle.coroutineScope.launch() {
                val game = viewModel.getGame(gameId).first()
                game.name= findViewById<EditText>(R.id.activity_edit_properties_game__name).text.toString()
                game.description= findViewById<EditText>(R.id.activity_edit_properties_game__description).text.toString()
                game.image = us
                viewModel.updateGame(game)
            }
            val intent = Intent(this, GameEditActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Observers
        /*viewModel.getGame(gameId).observe(this) { game ->
            findViewById<TextView>(R.id.activity_edit_properties_game__name).apply {
                text = game.name
            }
            findViewById<TextView>(R.id.activity_edit_properties_game__description).apply {
                text = game.description
            }
            findViewById<ImageView>(R.id.activity_edit_properties_game__image).apply {
                setImageURI(Uri.parse(game.image))
            }
        }*/

        lifecycle.coroutineScope.launch() {
            val game = viewModel.getGame(gameId).first()
            findViewById<TextView>(R.id.activity_edit_properties_game__name).apply {
                text = game.name
            }
            findViewById<TextView>(R.id.activity_edit_properties_game__description).apply {
                text = game.description
            }
            findViewById<ImageView>(R.id.activity_edit_properties_game__image).apply {
                setImageBitmap(BitmapFactory.decodeFile(game.image))
            }
            us=game.image.toString()
        }

        val selectImageIntent = registerForActivityResult(ActivityResultContracts.GetContent())
        { uri ->
            deleteToInternalStorage(us)
            val bitmap=BitmapFactory.decodeStream(contentResolver.openInputStream(uri!!))
            us = saveToInternalStorage(this,bitmap,getRandomString())
            findViewById<ImageView>(R.id.activity_edit_properties_game__image).apply {setImageBitmap(BitmapFactory.decodeFile(us)) }
        }
        findViewById<CardView>(R.id.activity_edit_properties_game__select_file).setOnClickListener {
            selectImageIntent.launch("image/*")
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
