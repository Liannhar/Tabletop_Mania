package com.tabletop.tabletopapplication.presentationlayer.models.game

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.Dice
import com.tabletop.tabletopapplication.presentationlayer.models.Hourglass.Hourglass
import com.tabletop.tabletopapplication.presentationlayer.models.Material.Material
import com.tabletop.tabletopapplication.presentationlayer.models.Note.Note
import com.tabletop.tabletopapplication.presentationlayer.models.Timer.Timer

@Database(entities = [Game::class, Note::class, Dice::class, Timer::class, Material::class,Hourglass::class], version = 1, exportSchema = false)
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