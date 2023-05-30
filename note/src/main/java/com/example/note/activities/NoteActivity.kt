package com.example.note.activities

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.note.R
import com.example.note.common.Connector


class NoteActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val text = intent?.extras?.run {
            getString("text", "")
        } ?: ""

        val position = intent?.extras?.run {
            getInt("position", -1)
        } ?: -1

        val saveButton = findViewById<ImageView>(R.id.activity_notes_save_button)
        val backButton = findViewById<ImageView>(R.id.activity_notes_back_button)
        val noteEdit = findViewById<EditText>(R.id.editText)

        noteEdit.setText(text)

        saveButton.setOnClickListener {
            Connector.setItem(Pair(position, noteEdit.text.toString()))
            setResult(RESULT_OK)
            finish()
        }

        backButton.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}