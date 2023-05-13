package com.tabletop.tabletopapplication.presentationlayer.adapters


import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates.DiceDelegate
import com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates.HourglassDelegate
import com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates.NoteDelegate
import com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates.TimerDelegate
import com.tabletop.tabletopapplication.presentationlayer.adapters.DiffCallbacks.ModelDiffCallback
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.models.game.Game
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel

class ModelAdapter(val context:FragmentActivity,gameDBViewModel: GameDBViewModel):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterDelegateManager = AdapterDelegatesManager<ArrayList<Model>>()
    private val items = ArrayList<Model>()

    init{
        adapterDelegateManager.addDelegate(DiceDelegate(this,context,gameDBViewModel))
            .addDelegate(HourglassDelegate(this,gameDBViewModel))
            .addDelegate(TimerDelegate(this,context,gameDBViewModel))
            .addDelegate(NoteDelegate(this,gameDBViewModel))
    }

    fun setItems(mitems:List<Model>){
        items.addAll(mitems)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun setItem(item:Model){
        items.add(item)
        notifyItemRangeInserted(this.items.size - items.size, 1)
    }

    fun updateItems(newItems: List<Model>) {
        val diffResult = DiffUtil.calculateDiff(ModelDiffCallback(items, newItems))
        items.clear()
        items.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
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