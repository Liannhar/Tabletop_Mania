package com.example.tabletopapplication.presentationlayer.models.Note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tabletopapplication.presentationlayer.models.Model


@Entity(tableName = "notesTable")
class Note (
    @ColumnInfo(name = "description") val noteDescription :String
    ): Model
{
    @PrimaryKey(autoGenerate = true) var id =0
}
