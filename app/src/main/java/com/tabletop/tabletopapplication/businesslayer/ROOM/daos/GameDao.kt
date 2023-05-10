package com.tabletop.tabletopapplication.businesslayer.ROOM.daos

import androidx.room.*
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.TimerROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.*
import kotlinx.coroutines.flow.Flow

interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(gameROOM: GameROOM)

    @Delete
    fun delete(gameROOM: GameROOM)

    @Update
    fun update(gameROOM: GameROOM)

    @Query("Select * from games order by id ASC")
    fun getAllGames(): Flow<List<GameROOM>>

    @Query("Select * from games Where id=:id")
    fun getGameById(id: Int): Flow<GameROOM?>

    @Query("SELECT * FROM games ORDER BY id DESC LIMIT 1")
    fun getLastGame(): Flow<GameROOM?>

    @Query("Select COUNT(*) FROM games")
    fun getCountGames(): Flow<Int>
}