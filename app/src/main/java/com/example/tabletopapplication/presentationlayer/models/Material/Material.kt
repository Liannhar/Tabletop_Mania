package com.example.tabletopapplication.presentationlayer.models.Material

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import java.util.*

@Entity(tableName = "materialsTable")
class Material (
    @ColumnInfo(name = "name") val name :String,
    @ColumnInfo(name = "description") val description :String,
    @ColumnInfo(name = "image") val image :String?,
    @PrimaryKey val id:Long
    )
{
}
