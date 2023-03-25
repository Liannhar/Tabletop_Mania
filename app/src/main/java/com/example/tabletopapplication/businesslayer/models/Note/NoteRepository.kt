package com.example.tabletopapplication.businesslayer.models.Note

import androidx.lifecycle.LiveData
import com.example.tabletopapplication.businesslayer.models.NotesDao

class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    fun getOneNote(id: Int):Note {
        return notesDao.getOneNote(id)
    }

    suspend fun insert(note: Note) {
        notesDao.insert(note)
    }

    suspend fun delete(note: Note){
        notesDao.delete(note)
    }

    suspend fun update(note: Note){
        notesDao.update(note)
    }
}