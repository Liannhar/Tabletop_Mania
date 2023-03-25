package com.example.tabletopapplication.businesslayer.models.Material

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tabletopapplication.businesslayer.models.Note.Note
import com.example.tabletopapplication.businesslayer.models.NotesDao

@Database(entities = arrayOf(Note::class), version = 1, exportSchema = false)
abstract class MaterialDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

    companion object {
        @Volatile
        private var INSTANCE: MaterialDatabase? = null

        fun getDatabase(context: Context): MaterialDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MaterialDatabase::class.java,
                    "note_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}