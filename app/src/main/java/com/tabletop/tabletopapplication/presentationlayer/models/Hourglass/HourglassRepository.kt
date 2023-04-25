package com.tabletop.tabletopapplication.presentationlayer.models.Hourglass

import com.tabletop.tabletopapplication.presentationlayer.models.DIce.Dice

import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDao
import kotlinx.coroutines.flow.Flow


class HourglassRepository(private val hourglassDao: GameDao) {

    val allDice: Flow<List<Dice>> = hourglassDao.getAllDice()

    fun getOneDice(id: Long): Flow<Hourglass> {
        return hourglassDao.getOneHourglass(id)
    }

    fun insert(hourglass: Hourglass) {
        hourglassDao.insert(hourglass)
    }

    fun delete(hourglass: Hourglass){
        hourglassDao.delete(hourglass)
    }

    fun update(hourglass: Hourglass){
        hourglassDao.update(hourglass)
    }
}