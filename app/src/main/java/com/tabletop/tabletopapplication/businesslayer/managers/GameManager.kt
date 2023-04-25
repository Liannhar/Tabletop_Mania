package com.tabletop.tabletopapplication.businesslayer.managers

import com.tabletop.tabletopapplication.businesslayer.models.GameEntity
import com.tabletop.tabletopapplication.businesslayer.providers.GameProvider


class GameManager {
    private val provider = GameProvider()

    fun getGame(id: Int, callback: (result: GameEntity?, error: Throwable?) -> Unit) {

        provider.getGame(id) { result, error ->
            callback(result, error)
        }
    }

    fun getGames(
        listId: ArrayList<Int>,
        callback: (result: ArrayList<GameEntity>?, error: Throwable?) -> Unit) {

        provider.getGames(listId) { result, error ->
            callback(result, error)
        }
    }
}