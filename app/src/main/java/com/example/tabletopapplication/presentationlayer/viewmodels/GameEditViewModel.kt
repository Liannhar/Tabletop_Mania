package com.example.tabletopapplication.presentationlayer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletopapplication.businesslayer.models.GameEntity

class GameEditViewModel: ViewModel() {

    private val MLDgame = MutableLiveData(GameEntity())
    val LDgame: LiveData<GameEntity>
        get() = MLDgame

    var game: GameEntity? = null
        get() = MLDgame.value
        set(value) {
            field = null
            if (value != null) {
                MLDgame.value = value!!
            }
        }

}