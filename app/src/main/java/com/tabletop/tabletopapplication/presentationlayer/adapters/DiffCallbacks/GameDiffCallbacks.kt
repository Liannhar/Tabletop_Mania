package com.tabletop.tabletopapplication.presentationlayer.adapters

import androidx.recyclerview.widget.DiffUtil
import com.tabletop.tabletopapplication.presentationlayer.models.Game

class GameDiffCallback(private val oldList: List<Game>, private val newList: List<Game>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}