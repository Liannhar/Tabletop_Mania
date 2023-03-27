package com.example.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.models.Note.NoteDatabase
import com.example.tabletopapplication.presentationlayer.models.Note.NoteRepository
import com.example.tabletopapplication.presentationlayer.models.Timer.Timer
import com.example.tabletopapplication.presentationlayer.models.Timer.TimerDatabase
import com.example.tabletopapplication.presentationlayer.models.Timer.TimerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TimerDBViewModel (application: Application) : AndroidViewModel(application) {

    val repository : TimerRepository

    init {
        val dao = TimerDatabase.getDatabase(application).getTimerDao()
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

    fun getTimer(id:Int) = repository.getOneTimer(id)

    fun getAllTimer() = repository.allTimer
}