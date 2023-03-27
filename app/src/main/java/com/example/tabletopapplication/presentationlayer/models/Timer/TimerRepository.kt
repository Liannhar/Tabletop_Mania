package com.example.tabletopapplication.presentationlayer.models.Timer

import androidx.lifecycle.LiveData
import com.example.tabletopapplication.businesslayer.models.NoteDao
import com.example.tabletopapplication.presentationlayer.models.Note.Note


class TimerRepository(private val timerDao: TimerDao) {

    val allTimer: LiveData<List<Timer>> = timerDao.getAllTimer()

    fun getOneTimer(id: Int): LiveData<Timer> {
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