package com.example.tabletopapplication.businesslayer.managers

import com.example.tabletopapplication.businesslayer.models.MaterialEntity
import com.example.tabletopapplication.businesslayer.providers.MaterialsProvider

class MaterialManager {
    private val provider = MaterialsProvider()

    fun getMaterial(id: Int, callback: (result: MaterialEntity?, error: Throwable?) -> Unit) {

        provider.getMaterial(id) { result, error ->
            callback(result, error)
        }
    }

    fun getMaterials(
        listId: ArrayList<Int>,
        callback: (result: ArrayList<MaterialEntity>?, error: Throwable?) -> Unit) {

        provider.getMaterials(listId) { result, error ->
            callback(result, error)
        }
    }

    fun getRangeMaterials(
        startId: Int,
        endId: Int,
        callback: (result: ArrayList<MaterialEntity>?, error: Throwable?) -> Unit) {

        provider.getRangeMaterials(startId, endId) { result, error ->
            callback(result, error)
        }
    }
}