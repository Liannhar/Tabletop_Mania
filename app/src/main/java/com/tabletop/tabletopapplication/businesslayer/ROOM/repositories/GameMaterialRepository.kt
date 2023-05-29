package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.DatabaseDao
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameMaterialROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import kotlinx.coroutines.flow.first

class GameMaterialRepository(private val dao: DatabaseDao) {

    fun insert(gameMaterial: GameMaterialROOM) = dao.insert(gameMaterial)
    fun delete(gameMaterial: GameMaterialROOM) = dao.delete(gameMaterial)
    fun update(gameMaterial: GameMaterialROOM) = dao.update(gameMaterial)

    suspend fun getGameMaterialsByGameId(id: Int) = dao.getGameMaterialsByGameId(id).first()

    suspend fun getMaterialsByGameId(id: Int) = dao.getMaterialsByGameId(id).first()
    suspend fun findGameMaterialById(gameId: Int, materialId: Int) = dao.findGameMaterialById(gameId, materialId).first()

    suspend fun getMaterialsWithExtrasByGameId(id: Int) =
        getMaterialsByGameId(id).zip(getGameMaterialsByGameId(id)).map {
            MaterialROOM(it.first).apply {
                extras = it.second.extras
            }
        }
}