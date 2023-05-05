package com.tabletop.tabletopapplication.businesslayer.ROOM.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tabletop.tabletopapplication.presentationlayer.models.Game

@Entity(tableName = "gameTable")
class GameROOM(
    @PrimaryKey                         override var id: Int,
    @ColumnInfo(name = "name")          override var name: String,
    @ColumnInfo(name = "description")   override var description: String,
    @ColumnInfo(name = "image")         override var image: String?
) : Game(), EntityROOM, java.io.Serializable {

    constructor(game: Game): this(game.id, game.name, game.description, game.image)

}