package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tabletop.tabletopapplication.presentationlayer.models.Timer.Timer
import com.tabletop.tabletopapplication.presentationlayer.models.Timer.TimerRepository
import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimerDBViewModel (application: Application) : AndroidViewModel(application) {

    val repository : TimerRepository

    init {
        val dao = GameDatabase.getDatabase(application).getGameDao()
        repository = TimerRepository(dao)

    }

    fun deleteTimer (timer: Timer) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(timer)
    }

    fun updateTimer(timer: Timer) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(timer)
    }

    fun addTimer(timer: Timer) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(timer)
    }

    fun getTimer(id:Long) = repository.getOneTimer(id)

    fun getAllTimer() = repository.allTimer
}