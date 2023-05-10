package com.tabletop.tabletopapplication.businesslayer.ROOM.entities

import androidx.room.*
import com.tabletop.tabletopapplication.presentationlayer.models.Material

@Entity(tableName = "materials", ignoredColumns = ["extras"])
class MaterialROOM(
    @PrimaryKey(autoGenerate = true)    override var id: Int = 0,
    @ColumnInfo(name = "name")          override var name: String,
    @ColumnInfo(name = "description")   override var description: String,
    @ColumnInfo(name = "image")         override var image: String?,
): Material(), EntityROOM {

    @Ignore
    constructor(name: String, description: String, image: String?): this(0, name, description, image)
    @Ignore
    constructor(material: Material) : this(material.id, material.name, material.description, material.image)

}
