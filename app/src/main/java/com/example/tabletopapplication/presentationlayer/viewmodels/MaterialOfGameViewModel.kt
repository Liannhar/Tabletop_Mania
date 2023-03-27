package com.example.tabletopapplication.presentationlayer.viewmodels
/*

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabletopapplication.presentationlayer.models.Materialofgame.MaterialOfGame
import com.example.tabletopapplication.presentationlayer.models.Materialofgame.MaterialOfGameDatabase
import com.example.tabletopapplication.presentationlayer.models.Materialofgame.MaterialOfGameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MaterialOfGameViewModel (application: Application) : AndroidViewModel(application) {

    val repository : MaterialOfGameRepository

    init {
        val dao = MaterialOfGameDatabase.getDatabase(application).getMaterialsOfGame()
        repository = MaterialOfGameRepository(dao)

    }

    fun deleteMaterialsOfGame (materialOfGame: MaterialOfGame) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(materialOfGame)
    }

    fun updateMaterialsOfGame(materialOfGame: MaterialOfGame) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(materialOfGame)
    }

    fun addMaterialsOfGame(materialOfGame: MaterialOfGame) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(materialOfGame)
    }

    fun getMaterialsOfGame(id:Int) = repository.getOneMaterialOfGame(id)

    fun getAllMaterialsOfGame() = repository.allMaterialOfGame
}*/
