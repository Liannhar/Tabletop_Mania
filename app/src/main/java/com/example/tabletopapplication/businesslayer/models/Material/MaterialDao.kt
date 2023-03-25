package com.example.tabletopapplication.businesslayer.models.Material

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopapplication.businesslayer.models.Note.Note

@Dao
interface MaterialDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(material: Material)

    @Delete
    suspend fun delete(material: Material)

    @Update
    suspend fun update(material: Material)

    @Query("Select * from materialsTable order by id ASC")
    fun getAllMaterials(): LiveData<List<Material>>

    @Query("Select * from materialsTable Where id=:id")
    fun getOneMaterial(id:Int): Material
}