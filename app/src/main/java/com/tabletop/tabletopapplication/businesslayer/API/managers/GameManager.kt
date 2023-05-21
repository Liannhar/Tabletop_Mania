package com.tabletop.tabletopapplication.businesslayer.API.managers

import com.tabletop.tabletopapplication.businesslayer.API.entities.GameAPI
import com.tabletop.tabletopapplication.businesslayer.API.entities.MaterialAPI
import com.tabletop.tabletopapplication.businesslayer.API.providers.GameProvider
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single


class GameManager {
    private val provider = GameProvider()

    suspend fun getGame(id: Int) = provider.getGame(id).single()

    suspend fun getGames(listId: List<Int>): List<GameAPI?> {
        val result = arrayListOf<GameAPI?>()

        listId.forEach { id ->
            result.add(getGame(id))
        }

        return result
    }

}