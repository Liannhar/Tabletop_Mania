package com.tabletop.tabletopapplication.presentationlayer.models.Materialofgame
/*

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MaterialOfGame::class], version = 1, exportSchema = false)
abstract class MaterialOfGameDatabase : RoomDatabase() {

    abstract fun getMaterialsOfGame(): MaterialOfGameDao

    companion object {
        @Volatile
        private var INSTANCE: MaterialOfGameDatabase? = null

        fun getDatabase(context: Context): MaterialOfGameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MaterialOfGameDatabase::class.java,
                    "materialsOfGame_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}*/
