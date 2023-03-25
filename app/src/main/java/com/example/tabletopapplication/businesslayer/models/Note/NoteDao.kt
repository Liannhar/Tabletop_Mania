package com.example.tabletopapplication.businesslayer.models


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopapplication.businesslayer.models.Note.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note : Note)

    @Delete
    suspend fun delete(note: Note)

    @Update
    suspend fun update(note: Note)

    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("Select * from notesTable Where id=:id")
    fun getOneNote(id:Int): Note
}