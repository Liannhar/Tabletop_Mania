package com.example.tabletopapplication.presentationlayer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tabletopapplication.businesslayer.managers.GameManager
import com.example.tabletopapplication.presentationlayer.models.LoadState

class GameViewModel: ViewModel() {

    val state = MutableLiveData<LoadState>()

    val manager: GameManager = GameManager()


    fun load() {
        state.postValue(LoadState.Pending())
        manager.getGame(3) { result, error ->
            when {
                result != null -> state.postValue(LoadState.Success(result))
                error != null -> state.postValue(LoadState.Error(error))
            }
        }
    }

}