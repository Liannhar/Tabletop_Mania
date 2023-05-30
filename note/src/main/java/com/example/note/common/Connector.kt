package com.example.note.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Connector {

    private val mutableItem = MutableLiveData<Pair<Int, Any?>>()
    val item: LiveData<Pair<Int, Any?>>
        get() = mutableItem

    fun setItem(value: Pair<Int, Any?>) {
        mutableItem.value = value
    }

}