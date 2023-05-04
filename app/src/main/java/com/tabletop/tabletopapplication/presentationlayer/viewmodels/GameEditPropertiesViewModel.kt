package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tabletop.tabletopapplication.businesslayer.API.entities.GameAPI

class GameEditPropertiesViewModel: ViewModel() {

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

}