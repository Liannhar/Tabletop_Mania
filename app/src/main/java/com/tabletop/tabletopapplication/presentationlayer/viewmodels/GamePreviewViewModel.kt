package com.tabletop.tabletopapplication.presentationlayer.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tabletop.tabletopapplication.presentationlayer.models.History

class GamePreviewViewModel: ViewModel() {

    private val mutableNewHistory = MutableLiveData<History>()
    val newHistory: LiveData<History>
        get() = mutableNewHistory

    fun setNewHistory(value: History) {
        mutableNewHistory.value = value
    }

}