package com.tabletop.tabletopapplication.businesslayer.ROOM.entities

import androidx.room.*

@Entity(
    tableName = "diceTable", foreignKeys = [ForeignKey(
        entity = GameROOM::class,
        parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["gameId"])]
)
class DiceROOM(
    @PrimaryKey val id: Int,
    @ColumnInfo val gameId: Int
) : EntityROOM