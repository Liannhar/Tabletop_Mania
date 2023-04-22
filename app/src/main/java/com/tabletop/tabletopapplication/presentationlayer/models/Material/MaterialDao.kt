package com.tabletop.tabletopapplication.presentationlayer.models.Material

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MaterialDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(material: Material)

    @Delete
    fun delete(material: Material)

    @Update
    fun update(material: Material)

    @Query("Select * from materialsTable order by id ASC")
    fun getAllMaterials(): LiveData<List<Material>>

    @Query("Select * from materialsTable Where id=:id")
    fun getOneMaterial(id:Long): Material
}