package com.tabletop.tabletopapplication.presentationlayer.models.Material

import androidx.lifecycle.LiveData
import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDao
import kotlinx.coroutines.flow.Flow

class MaterialRepository(private val materialDao: GameDao) {

    val allMaterials: Flow<List<Material>> = materialDao.getAllMaterials()

    fun getOneMaterial(id: Long): Material {
        return materialDao.getOneMaterial(id)
    }

    fun insert(material: Material) {
        materialDao.insert(material)
    }

    fun delete(material: Material){
        materialDao.delete(material)
    }

    fun update(material: Material){
        materialDao.update(material)
    }
}