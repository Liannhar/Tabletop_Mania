package com.example.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.models.Note.NoteDatabase
import com.example.tabletopapplication.presentationlayer.models.Note.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel (application: Application) : AndroidViewModel(application) {

    val repository : NoteRepository

    init {
        val dao = NoteDatabase.getDatabase(application).getNotesDao()
        repository = NoteRepository(dao)

    }

    fun deleteNote (note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }

    fun addNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }

    fun getNote(id:Int) = repository.getOneNote(id)

    fun getAllNotes() = repository.allNotes
}