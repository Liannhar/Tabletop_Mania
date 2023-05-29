package com.tabletop.tabletopapplication.businesslayer.API.providers


import com.tabletop.tabletopapplication.businesslayer.API.accessors.NetworkAccessor
import com.tabletop.tabletopapplication.businesslayer.API.entities.GameAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class GameProvider {

    fun getGame(id: Int) = flow {
        try {
            val result = NetworkAccessor.getService().getGame(id)
            emit(result.data.getOrNull(0))
        } catch (error: Throwable) {
            emit(null)
        }
    }

}