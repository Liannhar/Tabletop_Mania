package com.example.tabletopapplication.presentationlayer.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabletopapplication.businesslayer.managers.GameManager
import com.example.tabletopapplication.presentationlayer.models.LoadState
import kotlinx.coroutines.launch

class GameListViewModel: ViewModel() {

    val state = MutableLiveData<LoadState>()
    val manager: GameManager = GameManager()

    fun load() {
        viewModelScope.launch {
            state.postValue(LoadState.Pending())
            manager.getGame(1) { result, error ->
                when {
                    result != null -> state.postValue(LoadState.Success(result))
                    error != null -> state.postValue(LoadState.Error(error))
                }
            }
        }
    }

}