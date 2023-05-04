package com.tabletop.tabletopapplication.businesslayer.API.managers

import com.tabletop.tabletopapplication.businesslayer.API.entities.GameAPI
import com.tabletop.tabletopapplication.businesslayer.API.providers.GameProvider


class GameManager {
    private val provider = GameProvider()

    fun getGame(id: Int, callback: (result: GameAPI?, error: Throwable?) -> Unit) {

        provider.getGame(id) { result, error ->
            callback(result, error)
        }
    }

    fun getGames(
        listId: ArrayList<Int>,
        callback: (result: ArrayList<GameAPI>?, error: Throwable?) -> Unit) {

        provider.getGames(listId) { result, error ->
            callback(result, error)
        }
    }
}