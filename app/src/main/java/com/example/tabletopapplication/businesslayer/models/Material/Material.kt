package com.example.tabletopapplication.businesslayer.models.Material

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.Model

@Entity(tableName = "materialsTable")
class Material (
    @ColumnInfo(name = "name") val name :String,
    @ColumnInfo(name = "description") val description :String,
    @ColumnInfo(name = "image") val image :String):Model
{
    @PrimaryKey(autoGenerate = true) var id =0
}
