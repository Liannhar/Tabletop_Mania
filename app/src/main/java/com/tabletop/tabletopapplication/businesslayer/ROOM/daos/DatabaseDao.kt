package com.tabletop.tabletopapplication.businesslayer.ROOM.daos

import androidx.room.Dao

@Dao
interface DatabaseDao: GameDao, MaterialDao, OtherDao {



}