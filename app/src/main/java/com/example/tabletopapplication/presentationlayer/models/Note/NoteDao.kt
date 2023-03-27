package com.example.tabletopapplication.businesslayer.models


import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopapplication.presentationlayer.models.Note.Note

@Dao
interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(note : Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun update(note: Note)

    @Query("Select * from notesTable order by id ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Query("Select * from notesTable Where id=:id")
    fun getOneNote(id:Int): LiveData<Note>
}