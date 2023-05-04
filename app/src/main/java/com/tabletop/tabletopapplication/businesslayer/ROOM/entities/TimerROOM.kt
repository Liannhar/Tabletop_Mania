package com.tabletop.tabletopapplication.businesslayer.ROOM.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "timerTable",foreignKeys = [ForeignKey(entity = GameROOM::class,
    parentColumns = ["id"], childColumns = ["gameId"], onDelete = ForeignKey.CASCADE)],
    indices = [Index(value = ["gameId"])])
class TimerROOM (
    @PrimaryKey val id:Int,
    @ColumnInfo val gameId:Int,
): EntityROOM