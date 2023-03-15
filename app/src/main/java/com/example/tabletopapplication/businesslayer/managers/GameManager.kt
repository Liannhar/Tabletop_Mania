package com.example.tabletopapplication.businesslayer.managers

import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.businesslayer.providers.GamesProvider

class  GameManager {
    private val provider = GamesProvider()

    fun getGames(callback1: Int, callback: (result: List<GameEntity>?, error: Throwable?) -> Unit)  {
        provider.getGames { result, error ->
            when {

                result != null -> callback(result.map(GameMapper::map), null)

                error != null -> callback(null, error)
            }
        }
    }
}