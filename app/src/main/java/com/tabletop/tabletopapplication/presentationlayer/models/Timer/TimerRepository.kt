package com.tabletop.tabletopapplication.presentationlayer.models.Timer

import androidx.lifecycle.LiveData
import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDao
import kotlinx.coroutines.flow.Flow


class TimerRepository(private val timerDao: GameDao) {

    val allTimer: Flow<List<Timer>> = timerDao.getAllTimer()

    fun getOneTimer(id: Long): Flow<Timer> {
        return timerDao.getOneTimer(id)
    }

    fun insert(timer: Timer) {
        timerDao.insert(timer)
    }

    fun delete(timer: Timer){
        timerDao.delete(timer)
    }

    fun update(timer: Timer){
        timerDao.update(timer)
    }
}