package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.models.Note.Note
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.NoteViewModel


class NoteActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)
        val saveButton = findViewById<ImageView>(R.id.activity_notes_save_button)
        val backButton = findViewById<ImageView>(R.id.activity_notes_back_button)
        val noteEdit = findViewById<EditText>(R.id.editText)
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        val noteId = intent.getLongExtra("idnote",-1)
        val noteObserver = Observer<Note> { data ->
            noteEdit.setText(data.noteDescription)
        }
        viewModel.getNote(noteId).observe(this,noteObserver)


        saveButton.setOnClickListener {
            SaveNote(viewModel,noteEdit,noteId,gameId)

            startActivity(Intent(applicationContext,GamePreviewActivity::class.java))
            this.finish()
        }

        backButton.setOnClickListener {
            val noteDescription = noteEdit.text.toString()

            if (noteDescription.isNotEmpty()) {
                AlertDialog.Builder(this).setMessage("Do you want save note?")
                .setPositiveButton(
                    "Yes") { dialog, _ ->
                    SaveNote(viewModel,noteEdit,noteId,gameId)
                    Toast.makeText(this, "saved",Toast.LENGTH_SHORT).show()
                    dialog.cancel()

                    startActivity(Intent(applicationContext, GameEditActivity::class.java))
                    this.finish()
                }
                .setNegativeButton(
                    "No") { dialog, _ ->
                    startActivity(Intent(applicationContext, GameEditActivity::class.java))
                    dialog.cancel()
                }
                .show()
            }
        }
    }

    fun SaveNote(viewModel: NoteViewModel, noteEdit:EditText, noteId:Long,gameId:Long){
        val noteDescription = noteEdit.text.toString()
        val updatedNote = Note(noteDescription, gameId)
        updatedNote.id = noteId
        viewModel.updateNote(updatedNote)
        Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
    }
}