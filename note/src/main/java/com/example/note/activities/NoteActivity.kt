package com.example.note.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch



class NoteActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val materialId = intent.getIntExtra("MaterialId",-1)
        Log.i("WINWIN",materialId.toString()+"NM")
        val saveButton = findViewById<ImageView>(R.id.activity_notes_save_button)
        val backButton = findViewById<ImageView>(R.id.activity_notes_back_button)
        val noteEdit = findViewById<EditText>(R.id.editText)
        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DBViewModel::class.java]
        var material = Material()
        lifecycleScope.launch {
            material = viewModel.getMaterial(materialId)!!
            noteEdit.setText(material.extras )
        }

        saveButton.setOnClickListener {
            lifecycleScope.launch {
                material.setExtras(noteEdit.text.toString())
                viewModel.update(material)
                Toast.makeText(this@NoteActivity, "saved",Toast.LENGTH_SHORT).show()
                exitNote(viewModel)
            }
        }

        backButton.setOnClickListener {
            /*val noteDescription = noteEdit.text.toString()
            if (noteDescription.isNotEmpty()) {
                AlertDialog.Builder(this@NoteActivity).setMessage("Do you want save note?")
                    .setPositiveButton(
                        "Yes") { dialog, _ ->
                        Toast.makeText(this@NoteActivity, "saved",Toast.LENGTH_SHORT).show()
                        dialog.cancel()
                        lifecycleScope.launch {
                            material.setExtras(noteEdit.text.toString())
                            viewModel.update(material)
                            exitNote(viewModel)
                        }
                        }
                        .setNegativeButton(
                            "No") { dialog, _ ->
                            exitNote(viewModel)
                            dialog.cancel()

                        }
                        .show()
                }*/
            setResult(RESULT_CANCELED)
            finish()
        }
    }
    private fun exitNote(viewModel:DBViewModel)
    {
        lifecycleScope.launch {
            val currentGame = viewModel.getLastGame()
            Log.i("WINWIN",currentGame?.id.toString()+"N")
            setResult(RESULT_OK, Intent().apply {
                putExtra("Game", currentGame)
            })
            this@NoteActivity.finish()
        }
    }
}