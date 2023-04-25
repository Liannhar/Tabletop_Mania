package com.tabletop.tabletopapplication.presentationlayer.adapters.DiffCallbacks

import androidx.recyclerview.widget.DiffUtil
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.models.game.Game

class ModelDiffCallback(private val oldList: List<Model>, private val newList: List<Model>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].positionAdd == newList[newItemPosition].positionAdd
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}