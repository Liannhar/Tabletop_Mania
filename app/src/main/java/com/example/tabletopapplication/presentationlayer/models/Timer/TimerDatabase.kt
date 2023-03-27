package com.example.tabletopapplication.presentationlayer.models.Timer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tabletopapplication.businesslayer.models.NoteDao
import com.example.tabletopapplication.presentationlayer.models.Note.Note


@Database(entities = [Timer::class], version = 1, exportSchema = false)
abstract class TimerDatabase : RoomDatabase() {

    abstract fun getTimerDao():TimerDao

    companion object {
        @Volatile
        private var INSTANCE: TimerDatabase? = null

        fun getDatabase(context: Context): TimerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TimerDatabase::class.java,
                    "timer_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}