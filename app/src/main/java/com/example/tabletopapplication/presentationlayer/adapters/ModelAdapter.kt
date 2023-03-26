package com.example.tabletopapplication.presentationlayer.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.presentationlayer.adapters.Delegates.DiceDelegate
import com.example.tabletopapplication.presentationlayer.adapters.Delegates.NoteDelegate
import com.example.tabletopapplication.presentationlayer.adapters.Delegates.TimerDelegate
import com.example.tabletopapplication.presentationlayer.models.Model
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

class ModelAdapter(val editMode:Boolean=false):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterDelegateManager = AdapterDelegatesManager<ArrayList<Model>>()
    private val items = ArrayList<Model>()

    init{
        adapterDelegateManager.addDelegate(NoteDelegate(editMode))
        adapterDelegateManager.addDelegate(DiceDelegate(editMode))
        adapterDelegateManager.addDelegate(TimerDelegate(editMode))
    }

    fun setItems(mitems:List<Model>){
        this.items.clear()
        this.items.addAll(mitems)
        notifyItemRangeChanged(this.items.size - items.size, items.size)
    }

    fun setItem(item:Model){
        items.add(item)
        notifyItemChanged(items.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapterDelegateManager.onCreateViewHolder(parent,viewType)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapterDelegateManager.onBindViewHolder(items ,position,holder)
    }
}