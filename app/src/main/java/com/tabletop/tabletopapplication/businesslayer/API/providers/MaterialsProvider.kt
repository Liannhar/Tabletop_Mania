package com.tabletop.tabletopapplication.businesslayer.API.providers


import android.util.Log
import com.tabletop.tabletopapplication.businesslayer.API.accessors.NetworkAccessor
import com.tabletop.tabletopapplication.businesslayer.API.entities.MaterialAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MaterialsProvider {

    fun getMaterial(id: Int) = flow {
        try {
            val result = NetworkAccessor.getService().getMaterial(id)
            emit(result.data.getOrNull(0))
        } catch (error: Throwable) {
            Log.d("DEBUG", error.toString())
            emit(null)
        }
    }

    fun getRangeMaterials(startId: Int, endId: Int) = flow {
        try {
            val result = NetworkAccessor.getService().getRangeMaterials(startId, endId)
            emit(result.data.toList())
        } catch (error: Throwable) {
            Log.d("DEBUG", error.toString())
            emit(listOf())
        }
    }
}