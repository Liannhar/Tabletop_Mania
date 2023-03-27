package com.example.tabletopapplication.presentationlayer.models.DIce

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tabletopapplication.presentationlayer.models.Model

@Entity(tableName = "diceTable")
class Dice (
): Model
{
    @PrimaryKey(autoGenerate = true) var id =0
}