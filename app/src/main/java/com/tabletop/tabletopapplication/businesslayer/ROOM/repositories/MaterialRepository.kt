package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.MaterialDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class MaterialRepository(private val materialDao: MaterialDao) {

    val allMaterials: Flow<List<MaterialROOM>> = materialDao.getAllMaterials()

    suspend fun getMaterialById(id: Int) = materialDao.getMaterialById(id).first()

    fun insert(materialROOM: MaterialROOM) {
        materialDao.insert(materialROOM)
    }

    fun delete(materialROOM: MaterialROOM){
        materialDao.delete(materialROOM)
    }

    fun update(materialROOM: MaterialROOM){
        materialDao.update(materialROOM)
    }
}