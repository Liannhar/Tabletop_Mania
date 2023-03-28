package com.example.tabletopapplication.presentationlayer.models.Material

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopapplication.presentationlayer.models.Note.Note

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