package com.example.tabletopapplication.presentationlayer.models.DIce

import androidx.lifecycle.LiveData
import com.example.tabletopapplication.businesslayer.models.NoteDao
import com.example.tabletopapplication.presentationlayer.models.Note.Note


class DiceRepository(private val diceDao: DiceDao) {

    val allDice: LiveData<List<Dice>> = diceDao.getAllDice()

    fun getOneDice(id: Int): LiveData<Dice> {
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