package com.tabletop.tabletopapplication.presentationlayer.models.DIce

import androidx.lifecycle.LiveData

import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDao
import kotlinx.coroutines.flow.Flow


class DiceRepository(private val diceDao: GameDao) {

    val allDice: Flow<List<Dice>> = diceDao.getAllDice()

    fun getOneDice(id: Long): Flow<Dice> {
        return diceDao.getOneDice(id)
    }

    fun insert(dice: Dice) {
        diceDao.insert(dice)
    }

    fun delete(dice: Dice){
        diceDao.delete(dice)
    }

    fun update(dice: Dice){
        diceDao.update(dice)
    }
}