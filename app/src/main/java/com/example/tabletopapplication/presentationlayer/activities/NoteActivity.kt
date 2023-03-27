package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.viewmodels.NoteViewModel

class NoteActivity: AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val saveButton = findViewById<ImageView>(R.id.activity_notes_save_button)
        val backButton = findViewById<ImageView>(R.id.activity_notes_back_button)
        val noteEdit = findViewById<EditText>(R.id.editText)
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]

        val noteId = intent.getIntExtra("idnote",-1)
        val noteObserver = Observer<Note> { data ->
            val note = data
            noteEdit.setText(note.noteDescription)
        }
        viewModel.getNote(noteId).observe(this,noteObserver)


        saveButton.setOnClickListener {
            SaveNote(viewModel,noteEdit,noteId)
            startActivity(Intent(applicationContext,GamePreviewActivity::class.java))
            this.finish()
        }

        backButton.setOnClickListener {
            val noteDescription = noteEdit.text.toString()
            if (noteDescription.isNotEmpty()) {
                AlertDialog.Builder(this).setMessage("Do you want save note?")
                .setPositiveButton(
                    "Yes") { dialog, _ ->
                    SaveNote(viewModel,noteEdit,noteId)
                    Toast.makeText(this, "saved",Toast.LENGTH_SHORT).show()
                    dialog.cancel()
                }
                .setNegativeButton(
                    "No") { dialog, _ ->
                    dialog.cancel()
                }
                .show()
            }
            startActivity(Intent(applicationContext, GameEditActivity::class.java))
            this.finish()
        }
    }

    fun SaveNote(viewModel: NoteViewModel, noteEdit:EditText, noteId:Int){
        val noteDescription = noteEdit.text.toString()
        val updatedNote = Note(noteDescription)
        updatedNote.id = noteId
        viewModel.updateNote(updatedNote)
        Toast.makeText(this, "Note Updated..", Toast.LENGTH_LONG).show()
    }
}