package com.tabletop.tabletopapplication.businesslayer.providers


import com.tabletop.tabletopapplication.businesslayer.models.MaterialEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaterialsProvider {
    private val scope = CoroutineScope(Dispatchers.IO)

    fun getMaterial(
        id: Int,
        callback: (result: MaterialEntity?, error: Throwable?) -> Unit) =
        scope.launch {
            try {
                val result = com.tabletop.tabletopapplication.businesslayer.accessors.NetworkAccessor.getService().getMaterial(id)
                callback(result.data.getOrNull(0), null)
            } catch (error: Throwable) {
                callback(null, error)
            }
        }

    fun getMaterials(
        listId: ArrayList<Int>,
        callback: (result: ArrayList<MaterialEntity>?, error: Throwable?) -> Unit) =
        scope.launch {
            val result: ArrayList<MaterialEntity> = arrayListOf()
            var error: Throwable? = null
            for (id in listId) {
                 getMaterial(id) { id_result, id_error ->
                    when {
                        id_result != null -> result.add(id_result)
                        id_error != null -> error = id_error
                    }
                }.join()
            }
            callback(result.ifEmpty { null }, error)
        }

    fun getRangeMaterials(
        startId: Int,
        endId: Int,
        callback: (result: ArrayList<MaterialEntity>?, error: Throwable?) -> Unit) =
        scope.launch {
            try {
                val result = com.tabletop.tabletopapplication.businesslayer.accessors.NetworkAccessor.getService().getRangeMaterials(startId, endId)
                callback(result.data, null)
            } catch (error: Throwable) {
                callback(null, error)
            }
        }
}