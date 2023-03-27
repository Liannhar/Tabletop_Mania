package com.example.tabletopapplication.presentationlayer.models.Note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.models.Model

@Entity(tableName = "notesTable")
class Note (
    @ColumnInfo var noteDescription :String=""
    ): Model
{
    @PrimaryKey(autoGenerate = true) var id =0
}
