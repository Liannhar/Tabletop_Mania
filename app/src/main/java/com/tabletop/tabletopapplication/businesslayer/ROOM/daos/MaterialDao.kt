package com.tabletop.tabletopapplication.businesslayer.ROOM.daos

import androidx.room.*
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import kotlinx.coroutines.flow.Flow

interface MaterialDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(materialROOM: MaterialROOM)

    @Delete
    fun delete(materialROOM: MaterialROOM)

    @Update
    fun update(materialROOM: MaterialROOM)

    @Query("Select * from materials order by id ASC")
    fun getAllMaterials(): Flow<List<MaterialROOM>>

    @Query("Select * from materials Where id=:id")
    fun getMaterialById(id: Int): Flow<MaterialROOM?>

    @Query("SELECT * FROM materials WHERE name=:name")
    fun getMaterialByName(name: String): Flow<MaterialROOM?>

    @Query("SELECT COUNT(*) FROM materials")
    fun getCountMaterials(): Flow<Int>
}