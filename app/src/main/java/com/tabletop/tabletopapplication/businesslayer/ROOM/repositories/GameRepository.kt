package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.DatabaseDao
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.GameDao
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameMaterialROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import kotlinx.coroutines.flow.first

class GameRepository(private val dao: GameDao) {

    fun insert(gameROOM: GameROOM) = dao.insert(gameROOM)
    fun delete(gameROOM: GameROOM) = dao.delete(gameROOM)
    fun update(gameROOM: GameROOM) = dao.update(gameROOM)

    suspend fun getAllGames() = dao.getAllGames().first()
    suspend fun getGameById(id: Int) = dao.getGameById(id).first()
    suspend fun getLastGame() = dao.getLastGame().first()
    suspend fun getCountGames() = dao.getCountGames().first()
}