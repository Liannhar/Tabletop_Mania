package com.example.tabletopapplication.presentationlayer.models.Material

import androidx.lifecycle.LiveData
import com.example.tabletopapplication.presentationlayer.models.Note.Note

class MaterialRepository(private val materialDao: MaterialDao) {

    val allMaterials: LiveData<List<Material>> = materialDao.getAllMaterials()

    fun getOneMaterial(id: Long): Material {
        return materialDao.getOneMaterial(id)
    }

    suspend fun insert(material: Material) {
        materialDao.insert(material)
    }

    suspend fun delete(material: Material){
        materialDao.delete(material)
    }

    suspend fun update(material: Material){
        materialDao.update(material)
    }
}