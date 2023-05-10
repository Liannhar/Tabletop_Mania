package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.DatabaseDao
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.NoteROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.GameDao
import kotlinx.coroutines.flow.Flow

class NoteRepository(private val notesDao: DatabaseDao) {

    val allNotes: Flow<List<NoteROOM>> = notesDao.getAllNotes()

    fun getOneNote(id: Int): Flow<NoteROOM> {
        return notesDao.getOneNote(id)
    }

    fun insert(noteROOM: NoteROOM) {
        notesDao.insert(noteROOM)
    }

    fun delete(noteROOM: NoteROOM){
        notesDao.delete(noteROOM)
    }

    fun update(noteROOM: NoteROOM){
        notesDao.update(noteROOM)
    }
}