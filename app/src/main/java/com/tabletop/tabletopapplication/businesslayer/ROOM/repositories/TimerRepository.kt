package com.tabletop.tabletopapplication.businesslayer.ROOM.repositories

import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.TimerROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.daos.GameDao
import kotlinx.coroutines.flow.Flow


class TimerRepository(private val timerDao: GameDao) {

    val allTimerROOM: Flow<List<TimerROOM>> = timerDao.getAllTimer()

    fun getOneTimer(id: Int): Flow<TimerROOM> {
        return timerDao.getOneTimer(id)
    }

    fun insert(timerROOM: TimerROOM) {
        timerDao.insert(timerROOM)
    }

    fun delete(timerROOM: TimerROOM){
        timerDao.delete(timerROOM)
    }

    fun update(timerROOM: TimerROOM){
        timerDao.update(timerROOM)
    }
}