package com.example.tabletopapplication.presentationlayer.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.businesslayer.models.Model
import com.example.tabletopapplication.businesslayer.models.Note.Note
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

class ModelAdapter():RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterDelegateManager = AdapterDelegatesManager<ArrayList<Model>>()
    private val items = ArrayList<Model>()

    init{
        adapterDelegateManager.addDelegate(NoteDelegate())
    }

    fun setItems(items:List<Model>){
        this.items.clear()
        this.items.addAll(items)
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