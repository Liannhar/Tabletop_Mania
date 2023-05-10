package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.DatabaseDao
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.DiceROOM

import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.GameDao
import kotlinx.coroutines.flow.Flow


class DiceRepository(private val diceDao: DatabaseDao) {

    val allDiceROOM: Flow<List<DiceROOM>> = diceDao.getAllDice()

    fun getOneDice(id: Int): Flow<DiceROOM> {
        return diceDao.getOneDice(id)
    }

    fun insert(diceROOM: DiceROOM) {
        diceDao.insert(diceROOM)
    }

    fun delete(diceROOM: DiceROOM){
        diceDao.delete(diceROOM)
    }

    fun update(diceROOM: DiceROOM){
        diceDao.update(diceROOM)
    }
}