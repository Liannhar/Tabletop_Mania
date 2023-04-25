package com.tabletop.tabletopapplication.presentationlayer.models.game

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "gameTable")
class Game (
    @ColumnInfo(name = "name") var name :String,
    @ColumnInfo(name = "description") var description :String,
    @ColumnInfo(name = "image") var image :String?,
    @PrimaryKey val id:Long = (0..1000000).random().toLong(),
    @ColumnInfo var count:Int=0
)
{
}