package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.MaterialDao
import kotlinx.coroutines.flow.first

class MaterialRepository(private val dao: MaterialDao) {

    fun insert(materialROOM: MaterialROOM) = dao.insert(materialROOM)
    fun delete(materialROOM: MaterialROOM) = dao.delete(materialROOM)
    fun update(materialROOM: MaterialROOM) = dao.update(materialROOM)

    suspend fun getAllMaterials() = dao.getAllMaterials().first()
    suspend fun getMaterialById(id: Int) = dao.getMaterialById(id).first()
    suspend fun getMaterialByName(name: String) = dao.getMaterialByName(name).first()
    suspend fun getCountMaterials() = dao.getCountMaterials().first()
}