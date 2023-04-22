package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.Dice
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.DiceRepository
import com.tabletop.tabletopapplication.presentationlayer.models.game.GameDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DiceDBViewModel (application: Application) : AndroidViewModel(application) {

    val repository : DiceRepository

    init {
        val dao = GameDatabase.getDatabase(application).getGameDao()
        repository = DiceRepository(dao)

    }

    fun deleteDice (dice: Dice) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(dice)
    }

    fun updateDice(dice:Dice) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(dice)
    }

    fun addDice(dice:Dice) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(dice)
    }

    fun getDice(id:Long) = repository.getOneDice(id)

    fun getAllDice() = repository.allDice
}