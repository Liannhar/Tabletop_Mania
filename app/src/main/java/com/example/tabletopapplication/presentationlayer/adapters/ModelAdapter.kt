package com.example.tabletopapplication.presentationlayer.adapters

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.presentationlayer.adapters.Delegates.DiceDelegate
import com.example.tabletopapplication.presentationlayer.adapters.Delegates.NoteDelegate
import com.example.tabletopapplication.presentationlayer.adapters.Delegates.TimerDelegate
//import com.example.tabletopapplication.presentationlayer.models.Materialofgame.MaterialOfGame
import com.example.tabletopapplication.presentationlayer.models.Model
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

class ModelAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterDelegateManager = AdapterDelegatesManager<ArrayList<Model>>()
    private val items = ArrayList<Model>()

    init{
        adapterDelegateManager.addDelegate(DiceDelegate())
            .addDelegate(TimerDelegate())
            .addDelegate(NoteDelegate())
    }

    /*fun setAdapterDelegateManager(typeMaterial:Int,editMode: Boolean){
      when(typeMaterial){
          1->adapterDelegateManager.addDelegate(NoteDelegate(editMode))
          2->adapterDelegateManager.addDelegate(DiceDelegate(editMode))
          3->adapterDelegateManager.addDelegate(TimerDelegate(editMode))
      }
    }*/

    fun setItems(mitems:List<Model>){
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

    override fun getItemViewType(position: Int): Int {
        return adapterDelegateManager.getItemViewType(items, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapterDelegateManager.onBindViewHolder(items ,position,holder)
    }
}