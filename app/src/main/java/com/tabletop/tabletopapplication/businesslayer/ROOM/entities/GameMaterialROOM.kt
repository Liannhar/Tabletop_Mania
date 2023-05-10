package com.tabletop.tabletopapplication.businesslayer.ROOM.entities

import androidx.room.*

@Entity(
    tableName = "games_materials",
    foreignKeys = [ForeignKey(
        entity = GameROOM::class,
        parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE
    ), ForeignKey(
        entity = MaterialROOM::class,
        parentColumns = ["id"], childColumns = ["materialId"], onDelete = ForeignKey.CASCADE
    )]
)
class GameMaterialROOM(
    @PrimaryKey(autoGenerate = true)    var id: Int = 0,
    @ColumnInfo(name = "gameId")        var gameId: Int,
    @ColumnInfo(name = "materialId")    var materialId: Int,
    @ColumnInfo(name = "extras")        var extras: String
): EntityROOM {

    @Ignore
    constructor(gameId: Int, materialId: Int, extras: String): this(0, gameId, materialId, extras)

}