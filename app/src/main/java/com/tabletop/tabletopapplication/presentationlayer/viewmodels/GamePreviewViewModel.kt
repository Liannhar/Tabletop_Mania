package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tabletop.tabletopapplication.businesslayer.API.managers.MaterialManager
import com.tabletop.tabletopapplication.businesslayer.API.entities.GameAPI
import com.tabletop.tabletopapplication.businesslayer.API.entities.MaterialAPI
import com.tabletop.tabletopapplication.businesslayer.API.common.LoadState
import kotlinx.coroutines.launch

class GamePreviewViewModel : ViewModel() {

    private val MLDgame = MutableLiveData(GameAPI())

    val LDgame: LiveData<GameAPI>
        get() = MLDgame

    var game: GameAPI? = null
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
                    result != null -> MLDstate.value = LoadState.Success<List<MaterialAPI>>(result)
                    error != null -> MLDstate.value = LoadState.Error(error)
                }
            }
        }
    }




}