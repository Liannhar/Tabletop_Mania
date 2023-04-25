package com.tabletop.tabletopapplication.presentationlayer.models.Note

import androidx.lifecycle.LiveData
import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val notesDao: GameDao) {

    val allNotes: Flow<List<Note>> = notesDao.getAllNotes()

    fun getOneNote(id: Long): Flow<Note> {
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