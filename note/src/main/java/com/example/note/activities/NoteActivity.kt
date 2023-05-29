package com.example.note.activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.note.R
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch



class NoteActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        /*val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)
        val saveButton = findViewById<ImageView>(R.id.activity_notes_save_button)
        val backButton = findViewById<ImageView>(R.id.activity_notes_back_button)
        val noteEdit = findViewById<EditText>(R.id.editText)
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DBViewModel::class.java]
       *//* val noteObserver = Observer<Note> { data ->
            noteEdit.setText(data.noteDescription)
        }
        viewModel.getNote(noteId).observe(this,noteObserver)*//*



        saveButton.setOnClickListener {
            lifecycleScope.launch(){
                val game = viewModel.getGame(gameId).first()
                SaveNote(viewModel,noteEdit,noteId,gameId,game)
                game.count = game.count+1
                viewModel.updateGame(game)
                startActivity(Intent(applicationContext,GamePreviewActivity::class.java))
            }
            Toast.makeText(this, "saved",Toast.LENGTH_SHORT).show()
            viewModel.getGame(gameId).observe(this){
                SaveNote(viewModel,noteEdit,noteId,gameId,it)
                Toast.makeText(this, "saved",Toast.LENGTH_SHORT).show()
                it.count = it.count+1
                viewModel.updateGame(it)
                startActivity(Intent(applicationContext,GamePreviewActivity::class.java))
                this.finish()
            }

        }

        backButton.setOnClickListener {
            val noteDescription = noteEdit.text.toString()

            if (noteDescription.isNotEmpty()) {
                AlertDialog.Builder(this).setMessage("Do you want save note?")
                    .setPositiveButton(
                        "Yes") { dialog, _ ->
                        lifecycleScope.launch(){
                            val game = viewModel.getGame(gameId).first()
                            SaveNote(viewModel,noteEdit,noteId,gameId,game)
                            game.count = game.count+1
                            viewModel.updateGame(game)
                            startActivity(Intent(applicationContext, GamePreviewActivity::class.java))
                        }

                        Toast.makeText(this, "saved",Toast.LENGTH_SHORT).show()
                        dialog.cancel()
                        viewModel.getGame(gameId).observe(this){
                                SaveNote(viewModel,noteEdit,noteId,gameId,it)
                                Toast.makeText(this, "saved",Toast.LENGTH_SHORT).show()
                                dialog.cancel()
                                it.count = it.count+1
                                viewModel.updateGame(it)
                                startActivity(Intent(applicationContext, GameEditActivity::class.java))
                                this.finish()
                            }
                    }
                    .setNegativeButton(
                        "No") { dialog, _ ->
                        startActivity(Intent(applicationContext, GamePreviewActivity::class.java))
                        dialog.cancel()
                    }
                    .show()
            }
        }
    }

    fun SaveNote(viewModel: DBViewModel, noteEdit:EditText, noteId:Long,gameId:Long,game:Game){
        val noteDescription = noteEdit.text.toString()
        val updatedNote = NoteROOM(noteDescription, gameId, positionAdd = game.count)
        updatedNote.id = noteId
        viewModel.updateNote(updatedNote)
        Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
    }*/
    }
}