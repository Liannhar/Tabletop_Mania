package com.tabletop.tabletopapplication.presentationlayer.models.DIce

import androidx.lifecycle.LiveData

import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDao


class DiceRepository(private val diceDao: GameDao) {

    val allDice: LiveData<List<Dice>> = diceDao.getAllDice()

    fun getOneDice(id: Long): LiveData<Dice> {
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