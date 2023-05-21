package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.DatabaseDao
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameMaterialROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import kotlinx.coroutines.flow.first

class GameRepository(private val dao: DatabaseDao) {

    fun insert(gameROOM: GameROOM) = dao.insert(gameROOM)
    fun delete(gameROOM: GameROOM) = dao.delete(gameROOM)
    fun update(gameROOM: GameROOM) = dao.update(gameROOM)

    suspend fun getAllGames() = dao.getAllGames().first()
    suspend fun getGameById(id: Int) = dao.getGameById(id).first()
    suspend fun getLastGame() = dao.getLastGame().first()
    suspend fun getCountGames() = dao.getCountGames().first()

    suspend fun getMaterialsByGameId(id: Int) = dao.getMaterialsByGameId(id).first()
    private suspend fun findGameMaterialById(gameId: Int, materialId: Int) =
        dao.findGameMaterialById(gameId, materialId).first()

    fun addMaterialToGame(gameId: Int, material: MaterialROOM) =
        dao.insert(GameMaterialROOM(gameId, material.id, material.extras))
    suspend fun deleteMaterialFromGame(gameId: Int, materialId: Int) =
        findGameMaterialById(gameId, materialId)?.let {
            dao.delete(it)
        }
    suspend fun updateMaterialAtGame(gameId: Int, material: MaterialROOM) =
        findGameMaterialById(gameId, material.id)?.let {
            it.extras = material.extras
            dao.update(it)
        }
}