package com.example.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabletopapplication.presentationlayer.models.DIce.Dice
import com.example.tabletopapplication.presentationlayer.models.DIce.DiceDatabase
import com.example.tabletopapplication.presentationlayer.models.DIce.DiceRepository
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.models.Note.NoteDatabase
import com.example.tabletopapplication.presentationlayer.models.Note.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiceDBViewModel (application: Application) : AndroidViewModel(application) {

    val repository : DiceRepository

    init {
        val dao = DiceDatabase.getDatabase(application).getDiceDao()
        repository = DiceRepository(dao)

    }

    fun deleteDice (dice:Dice) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(dice)
    }

    fun updateDice(dice:Dice) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(dice)
    }

    fun addDice(dice:Dice) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(dice)
    }

    fun getDice(id:Int) = repository.getOneDice(id)

    fun getAllDice() = repository.allDice
}