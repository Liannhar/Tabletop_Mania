package com.example.tabletopapplication.presentationlayer.adapters

import android.app.Activity
import android.util.Log
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.presentationlayer.adapters.Delegates.DiceDelegate
import com.example.tabletopapplication.presentationlayer.adapters.Delegates.NoteDelegate
import com.example.tabletopapplication.presentationlayer.adapters.Delegates.TimerDelegate
//import com.example.tabletopapplication.presentationlayer.models.Materialofgame.MaterialOfGame
import com.example.tabletopapplication.presentationlayer.models.Model
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

class ModelAdapter(val context:FragmentActivity,val gameId:Long):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterDelegateManager = AdapterDelegatesManager<ArrayList<Model>>()
    private val items = ArrayList<Model>()

    init{
        adapterDelegateManager.addDelegate(DiceDelegate(gameId))
            .addDelegate(TimerDelegate(context))
            .addDelegate(NoteDelegate(gameId))
    }

    fun setItems(mitems:List<Model>){
        items.addAll(mitems)
        notifyItemRangeInserted(this.items.size - items.size, mitems.size)
    }

    fun setItem(item:Model){
        items.add(item)
        notifyItemRangeInserted(this.items.size - items.size, 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapterDelegateManager.onCreateViewHolder(parent,viewType)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return adapterDelegateManager.getItemViewType(items, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapterDelegateManager.onBindViewHolder(items ,position,holder)
    }
}