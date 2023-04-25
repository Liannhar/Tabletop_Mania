package com.tabletop.tabletopapplication.businesslayer.providers


import com.tabletop.tabletopapplication.businesslayer.models.GameEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameProvider {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getGame(
        id: Int,
        callback: (result: GameEntity?, error: Throwable?) -> Unit) =
        scope.launch {
            try {
                val result = com.tabletop.tabletopapplication.businesslayer.accessors.NetworkAccessor.getService().getGame(id)
                callback(result.data.getOrNull(0), null)
            } catch (error: Throwable) {
                callback(null, error)
            }
        }

    fun getGames(
        listId: ArrayList<Int>,
        callback: (result: ArrayList<GameEntity>?, error: Throwable?) -> Unit) =
        scope.launch {
            val result: ArrayList<GameEntity> = arrayListOf()
            var error: Throwable? = null
            for (id in listId) {
                getGame(id) { id_result, id_error ->
                    when {
                        id_result != null -> {
                            result.add(id_result)
                        }
                        id_error != null -> error = id_error
                    }
                }.join()
            }
            callback(result.ifEmpty { null }, error)
        }
}