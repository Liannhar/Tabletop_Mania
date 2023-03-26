package com.example.tabletopapplication.presentationlayer.models.Note

import androidx.lifecycle.LiveData
import com.example.tabletopapplication.businesslayer.models.NotesDao

class NoteRepository(private val notesDao: NotesDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    fun getOneNote(id: Int): LiveData<Note> {
        return notesDao.getOneNote(id)
    }

    fun insert(note: Note) {
        notesDao.insert(note)
    }

    fun delete(note: Note){
        notesDao.delete(note)
    }

    fun update(note: Note){
        notesDao.update(note)
    }
}