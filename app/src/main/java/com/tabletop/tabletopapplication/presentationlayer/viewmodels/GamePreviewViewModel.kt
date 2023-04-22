package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tabletop.tabletopapplication.businesslayer.managers.MaterialManager
import com.tabletop.tabletopapplication.businesslayer.models.GameEntity
import com.tabletop.tabletopapplication.businesslayer.models.MaterialEntity
import com.tabletop.tabletopapplication.presentationlayer.models.LoadState
import kotlinx.coroutines.launch

class GamePreviewViewModel : ViewModel() {

    private val MLDgame = MutableLiveData(GameEntity())

    val LDgame: LiveData<GameEntity>
        get() = MLDgame

    var game: GameEntity? = null
        get() = MLDgame.value
        set(value) {
            field = null
            if (value != null) {
                MLDgame.value = value
            }
        }

    private val MLDstate = MutableLiveData<LoadState>()
    val LDstate: LiveData<LoadState>
        get() = MLDstate

    private val materialManager = MaterialManager()

    fun load() {
        viewModelScope.launch {
            MLDstate.value = LoadState.Pending()
            materialManager.getMaterials(game?.materials!!) { result, error ->
                when {
                    result != null -> MLDstate.value = LoadState.Success<List<MaterialEntity>>(result)
                    error != null -> MLDstate.value = LoadState.Error(error)
                }
            }
        }
    }




}