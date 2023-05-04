package com.tabletop.tabletopapplication.businesslayer.ROOM

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.TimerROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.*
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.GameDao

@Database(
    entities = [GameROOM::class, NoteROOM::class, DiceROOM::class, TimerROOM::class, MaterialROOM::class, HourglassROOM::class],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {

    abstract fun getGameDao(): GameDao

    companion object {
        @Volatile
        private var INSTANCE: GameDatabase? = null

        fun getDatabase(context: Context): GameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameDatabase::class.java,
                    "game_database"
                ).createFromAsset("database/game_database.db").build()
                INSTANCE = instance
                instance
            }
        }
    }
}