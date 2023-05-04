package com.tabletop.tabletopapplication.businesslayer.ROOM.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tabletop.tabletopapplication.presentationlayer.models.Material

@Entity(tableName = "materialsTable")
class MaterialROOM(
    @PrimaryKey                         override var id: Int,
    @ColumnInfo(name = "name")          override var name: String,
    @ColumnInfo(name = "description")   override var description: String,
    @ColumnInfo(name = "image")         override var image: String?,
): Material(), EntityROOM, java.io.Serializable
