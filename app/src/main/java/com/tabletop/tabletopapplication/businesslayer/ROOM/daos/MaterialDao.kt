package com.tabletop.tabletopapplication.businesslayer.ROOM.daos

import androidx.room.*
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import kotlinx.coroutines.flow.Flow

interface MaterialDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(materialROOM: MaterialROOM)

    @Delete
    fun delete(materialROOM: MaterialROOM)

    @Update
    fun update(materialROOM: MaterialROOM)

    @Query("Select * from materials order by id ASC")
    fun getAllMaterials(): Flow<List<MaterialROOM>>

    @Query("Select * from materials Where id=:id")
    fun getMaterialById(id: Int): Flow<MaterialROOM?>

    @Query("SELECT COUNT(*) FROM materials")
    fun getCountMaterials(): Flow<Int>

//    @Query("SELECT * FROM materials WHERE id IN (SELECT materialId FROM gmTable WHERE gameId = :id)")
//    fun getMaterialsByGameId(id: Int): Flow<List<MaterialROOM>>
//
//    fun getMaterialsByGame(game: GameROOM) = getMaterialsByGameId(game.id)
}