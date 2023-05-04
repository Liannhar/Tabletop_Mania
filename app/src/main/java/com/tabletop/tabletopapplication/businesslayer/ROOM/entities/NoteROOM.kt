package com.tabletop.tabletopapplication.businesslayer.ROOM.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "notesTable", foreignKeys = [ForeignKey(
        entity = GameROOM::class,
        parentColumns = ["id"], childColumns = ["gameId"], onDelete = CASCADE
    )],
    indices = [Index("gameId")]
)
class NoteROOM(
    @PrimaryKey var id: Int,
    @ColumnInfo(name = "gameId") val gameId: Int,
    @ColumnInfo var noteDescription: String,
) : EntityROOM {
}
