package com.example.tabletopapplication.businesslayer.providers


import com.example.tabletopapplication.businesslayer.accessors.NetworkAccessor
import com.example.tabletopapplication.businesslayer.models.GameEntity
import kotlinx.coroutines.*

class GamesProvider {
    val scope = CoroutineScope(Dispatchers.IO)

    fun getGames(callback: (result: List<GameEntity>?, error: Throwable?) -> Unit) {
        scope.launch {
            try {
                val result =  NetworkAccessor.requireService().getGameList()
                withContext(Dispatchers.Main) {
                    callback(result, null)
                }
            } catch (error: Throwable) {
                withContext(Dispatchers.Main) {
                    callback(null, error)
                }
            }

        }
    }
}