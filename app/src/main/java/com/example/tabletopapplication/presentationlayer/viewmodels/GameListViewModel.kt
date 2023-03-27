package com.example.tabletopapplication.presentationlayer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tabletopapplication.businesslayer.managers.GameManager
import com.example.tabletopapplication.presentationlayer.models.LoadState
import kotlinx.coroutines.launch

class GameListViewModel: ViewModel() {

    private val MLDstate = MutableLiveData<LoadState>()
    val LDstate: LiveData<LoadState>
        get() = MLDstate

    private val gameManager: GameManager = GameManager()

    fun load() {
        viewModelScope.launch {
            MLDstate.postValue(LoadState.Pending())
            gameManager.getGame(1) { result, error ->
                when {
                    result != null -> MLDstate.postValue(LoadState.Success(result))
                    error != null -> MLDstate.postValue(LoadState.Error(error))
                }
            }
        }
    }

}