package com.example.tabletopapplication.presentationlayer.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletopapplication.businesslayer.managers.GameManager

class GameViewModel: ViewModel() {

    val state = MutableLiveData<LoadState>()

    val manager: GameManager = GameManager()


    fun load() {
        state.postValue(LoadState.Pending())
        manager.getGames(1)
        { result, error ->
            when {
                result != null -> state.postValue(LoadState.Success(result))
                error != null -> state.postValue(LoadState.Error(error))
            }
        }
    }

}