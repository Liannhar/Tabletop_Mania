package com.example.tabletopapplication.presentationlayer.models.Note

import androidx.lifecycle.LiveData
import com.example.tabletopapplication.presentationlayer.models.game.GameDao

class NoteRepository(private val notesDao: GameDao) {

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    fun getOneNote(id: Long): LiveData<Note> {
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