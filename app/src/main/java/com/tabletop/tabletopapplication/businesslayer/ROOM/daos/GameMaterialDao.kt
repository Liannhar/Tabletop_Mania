package com.tabletop.tabletopapplication.businesslayer.ROOM.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameMaterialROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import kotlinx.coroutines.flow.Flow

interface GameMaterialDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(gameMaterial: GameMaterialROOM)

    @Delete
    fun delete(gameMaterial: GameMaterialROOM)

    @Update
    fun update(gameMaterial: GameMaterialROOM)

    @Query("SELECT materials.* FROM materials JOIN (SELECT * FROM games_materials WHERE gameId = :id) mIds ON materials.id = mIds.materialId")
    fun getMaterialsByGameId(id: Int): Flow<List<MaterialROOM>>

    fun addMaterialToGame(game: GameROOM, material: MaterialROOM) {
        insert(GameMaterialROOM(game.id, material.id, material.extras))
    }
}