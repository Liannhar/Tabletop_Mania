package com.tabletop.tabletopapplication.businesslayer.ROOM.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "gmTable",
    foreignKeys = [ForeignKey(
        entity = GameROOM::class,
        parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MaterialROOM::class,
        parentColumns = ["id"], childColumns = ["materialId"], onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["gameId"])]
)
class GameMaterialROOM(
    @ColumnInfo(name = "gameId") var gameId: Int,
    @ColumnInfo(name = "materialId") var materialId: Int,
)