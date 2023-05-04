package com.tabletop.tabletopapplication.businesslayer.ROOM.entities

import androidx.room.*

@Entity(
    tableName = "hourglassTable", foreignKeys = [ForeignKey(
        entity = GameROOM::class,
        parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["gameId"])]
)
class HourglassROOM(
    @PrimaryKey val id: Int,
    @ColumnInfo val gameId: Int
) : EntityROOM