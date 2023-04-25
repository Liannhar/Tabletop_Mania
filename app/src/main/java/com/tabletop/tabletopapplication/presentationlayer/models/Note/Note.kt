package com.tabletop.tabletopapplication.presentationlayer.models.Note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.models.game.Game

@Entity(tableName = "notesTable",foreignKeys = [ForeignKey(entity = Game::class,
    parentColumns = ["id"], childColumns = ["gameId"], onDelete = CASCADE)],
    indices = [Index("gameId")])
class Note (
    @ColumnInfo var noteDescription :String="",
    @ColumnInfo(name = "gameId") val  gameId:Long,
    @PrimaryKey var id:Long = (0..1000000).random().toLong(),
    @ColumnInfo override val positionAdd: Int
): Model
{
}
