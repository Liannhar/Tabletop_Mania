package com.tabletop.tabletopapplication.presentationlayer.models.DIce

import androidx.room.*
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.models.game.Game
import java.util.*

@Entity(tableName = "diceTable",foreignKeys = [ForeignKey(entity = Game::class,
    parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["gameId"])])
class Dice (
    @ColumnInfo val gameId:Long,
    @PrimaryKey val id:Long = (0..1000000).random().toLong()
): Model
{}