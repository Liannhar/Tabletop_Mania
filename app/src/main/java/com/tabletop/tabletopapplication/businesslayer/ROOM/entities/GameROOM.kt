package com.tabletop.tabletopapplication.businesslayer.ROOM.entities

import androidx.room.*
import com.tabletop.tabletopapplication.presentationlayer.models.Game

@Entity(tableName = "games")
class GameROOM(
    @PrimaryKey(autoGenerate = true)    override var id: Int = 0,
    @ColumnInfo(name = "name")          override var name: String,
    @ColumnInfo(name = "description")   override var description: String,
    @ColumnInfo(name = "image")         override var image: String?
) : Game(), EntityROOM {

    @Ignore
    constructor(name: String, description: String, image: String?): this(0, name, description, image)
    @Ignore
    constructor(game: Game): this(game.id, game.name, game.description, game.image)

}