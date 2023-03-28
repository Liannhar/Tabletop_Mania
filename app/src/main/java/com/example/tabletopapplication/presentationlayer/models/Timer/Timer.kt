package com.example.tabletopapplication.presentationlayer.models.Timer

import androidx.room.*
import com.example.tabletopapplication.presentationlayer.models.Model
import com.example.tabletopapplication.presentationlayer.models.game.Game
import java.util.*

@Entity(tableName = "timerTable",foreignKeys = [ForeignKey(entity = Game::class,
    parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["gameId"])])
class Timer (
    @ColumnInfo val gameId:Long,
    @PrimaryKey val id:Long = (0..1000000).random().toLong()
): Model
{
}