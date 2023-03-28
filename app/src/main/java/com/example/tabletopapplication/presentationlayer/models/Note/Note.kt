package com.example.tabletopapplication.presentationlayer.models.Note

import androidx.room.*
import androidx.room.ForeignKey.CASCADE
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.models.Model
import com.example.tabletopapplication.presentationlayer.models.game.Game
import java.util.*

@Entity(tableName = "notesTable",foreignKeys = [ForeignKey(entity = Game::class,
    parentColumns = ["id"], childColumns = ["gameId"], onDelete = CASCADE)],
    indices = [Index("gameId")])
class Note (
    @ColumnInfo var noteDescription :String="",
    @ColumnInfo(name = "gameId") val  gameId:Long,
    @PrimaryKey var id:Long = (0..1000000).random().toLong()
    ): Model
{
}
