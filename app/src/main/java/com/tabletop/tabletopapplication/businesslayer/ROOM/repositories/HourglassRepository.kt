package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.DatabaseDao
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.DiceROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.HourglassROOM

import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.GameDao
import kotlinx.coroutines.flow.Flow


class HourglassRepository(private val hourglassDao: DatabaseDao) {

    val allDiceROOM: Flow<List<DiceROOM>> = hourglassDao.getAllDice()

    fun getOneDice(id: Int): Flow<HourglassROOM> {
        return hourglassDao.getOneHourglass(id)
    }

    fun insert(hourglassROOM: HourglassROOM) {
        hourglassDao.insert(hourglassROOM)
    }

    fun delete(hourglassROOM: HourglassROOM){
        hourglassDao.delete(hourglassROOM)
    }

    fun update(hourglassROOM: HourglassROOM){
        hourglassDao.update(hourglassROOM)
    }
}