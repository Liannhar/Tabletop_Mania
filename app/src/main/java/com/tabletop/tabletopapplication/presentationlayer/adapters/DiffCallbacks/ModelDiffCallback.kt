package com.tabletop.tabletopapplication.presentationlayer.adapters.DiffCallbacks

import androidx.recyclerview.widget.DiffUtil
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.EntityROOM

class ModelDiffCallback(private val oldList: List<EntityROOM>, private val newList: List<EntityROOM>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldList[oldItemPosition].positionAdd == newList[newItemPosition].positionAdd
        return true
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}