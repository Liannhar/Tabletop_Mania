package com.tabletop.tabletopapplication.businesslayer.API.managers

import com.tabletop.tabletopapplication.businesslayer.API.entities.MaterialAPI
import com.tabletop.tabletopapplication.businesslayer.API.providers.MaterialsProvider


class MaterialManager {
    private val provider = MaterialsProvider()

    fun getMaterial(id: Int, callback: (result: MaterialAPI?, error: Throwable?) -> Unit) {

        provider.getMaterial(id) { result, error ->
            callback(result, error)
        }
    }

    fun getMaterials(
        listId: ArrayList<Int>,
        callback: (result: ArrayList<MaterialAPI>?, error: Throwable?) -> Unit) {

        provider.getMaterials(listId) { result, error ->
            callback(result, error)
        }
    }

    fun getRangeMaterials(
        startId: Int,
        endId: Int,
        callback: (result: ArrayList<MaterialAPI>?, error: Throwable?) -> Unit) {

        provider.getRangeMaterials(startId, endId) { result, error ->
            callback(result, error)
        }
    }
}