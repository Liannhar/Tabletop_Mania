package com.example.tabletopapplication.presentationlayer.models.Timer

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tabletopapplication.presentationlayer.models.Model

@Entity(tableName = "timerTable")
class Timer (
): Model
{
    @PrimaryKey(autoGenerate = true) var id =0
}