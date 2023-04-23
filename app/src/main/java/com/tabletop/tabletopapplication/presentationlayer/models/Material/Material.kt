package com.tabletop.tabletopapplication.presentationlayer.models.Material

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "materialsTable")
class Material (
    @ColumnInfo(name = "name") val name :String,
    @ColumnInfo(name = "description") val description :String,
    @ColumnInfo(name = "image") val image :String?,
    @PrimaryKey val id:Long = (0..1000000).random().toLong()
    )
{
}