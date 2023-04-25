package com.tabletop.tabletopapplication.presentationlayer.models.Timer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.models.game.Game

@Entity(tableName = "timerTable",foreignKeys = [ForeignKey(entity = Game::class,
    parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["gameId"])])
class Timer (
    @ColumnInfo val gameId:Long,
    @PrimaryKey val id:Long = (0..1000000).random().toLong(),
    @ColumnInfo override val positionAdd: Int
): Model
{
}