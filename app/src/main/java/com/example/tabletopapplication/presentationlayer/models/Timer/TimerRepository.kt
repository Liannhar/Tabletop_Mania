package com.example.tabletopapplication.presentationlayer.models.Timer

import androidx.lifecycle.LiveData
import com.example.tabletopapplication.presentationlayer.models.game.GameDao


class TimerRepository(private val timerDao: GameDao) {

    val allTimer: LiveData<List<Timer>> = timerDao.getAllTimer()

    fun getOneTimer(id: Long): LiveData<Timer> {
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