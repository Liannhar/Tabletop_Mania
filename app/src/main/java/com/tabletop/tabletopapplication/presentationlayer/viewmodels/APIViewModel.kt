package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import androidx.lifecycle.ViewModel
import com.tabletop.tabletopapplication.businesslayer.API.managers.GameManager
import com.tabletop.tabletopapplication.businesslayer.API.managers.MaterialManager
import com.tabletop.tabletopapplication.presentationlayer.models.Game
import com.tabletop.tabletopapplication.presentationlayer.models.Material

class APIViewModel: ViewModel() {

    private val gameManager = GameManager()
    private val materialManager = MaterialManager()

    suspend fun getGame(id: Int) = gameManager.getGame(id)?.toGame()
    suspend fun getGames(listId: List<Int>) = gameManager.getGames(listId).map { item ->
        item?.toGame()
    }

    suspend fun getMaterial(id: Int) = materialManager.getMaterial(id)?.toMaterial()
    suspend fun getMaterials(listId: List<Int>) = materialManager.getMaterials(listId).map { item ->
        item?.toMaterial()
    }
    suspend fun getRangeMaterials(startId: Int, endId: Int) = materialManager.getRangeMaterials(startId, endId).map { item ->
        item.toMaterial()
    }

}