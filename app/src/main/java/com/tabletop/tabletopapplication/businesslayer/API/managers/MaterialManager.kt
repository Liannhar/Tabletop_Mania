package com.tabletop.tabletopapplication.businesslayer.API.managers

import com.tabletop.tabletopapplication.businesslayer.API.entities.MaterialAPI
import com.tabletop.tabletopapplication.businesslayer.API.providers.MaterialsProvider
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single


class MaterialManager {
    private val provider = MaterialsProvider()

    suspend fun getMaterial(id: Int) = provider.getMaterial(id).single()
    suspend fun getRangeMaterials(startId: Int, endId: Int) =
        provider.getRangeMaterials(startId, endId).single()

    suspend fun getMaterials(listId: List<Int>): List<MaterialAPI?> {
        val result = arrayListOf<MaterialAPI?>()

        listId.forEach { id ->
            result.add(getMaterial(id))
        }

        return result
    }
}