package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.GameDao
import kotlinx.coroutines.flow.Flow

class MaterialRepository(private val materialDao: GameDao) {

    val allMaterials: Flow<List<MaterialROOM>> = materialDao.getAllMaterials()

    fun getOneMaterial(id: Int): MaterialROOM {
        return materialDao.getOneMaterial(id)
    }

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