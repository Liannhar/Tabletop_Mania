package com.tabletop.tabletopapplication.presentationlayer.adapters


import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.tabletop.tabletopapplication.presentationlayer.adapters.DiffCallbacks.ModelDiffCallback
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.launch

class DelegateMaterialsAdapter(
    private val context: Context,
    private val materials: ArrayList<Material> = arrayListOf(),
    private val databaseVM: DBViewModel = DBViewModel((context as Activity).application)
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterDelegateManager = AdapterDelegatesManager<ArrayList<Material>>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        databaseVM.viewModelScope.launch {
            databaseVM.getAllMaterials().forEach { material ->
                val ref = Class.forName("com.example.${material.name}.Delegate")
                adapterDelegateManager.addDelegate(ref.newInstance() as AdapterDelegate<ArrayList<Material>>)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return adapterDelegateManager.onCreateViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return materials.size
    }

    override fun getItemViewType(position: Int): Int {
        return adapterDelegateManager.getItemViewType(materials, position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        adapterDelegateManager.onBindViewHolder(materials, position, holder)
    }

    fun getMaterials() = materials

    fun add(item: Material) {
        materials.add(item)
        notifyItemInserted(materials.size - 1)
    }

    fun addAll(list: List<Material>) {
        materials.addAll(list)
        notifyItemRangeInserted(materials.size - list.size, list.size)
    }

    fun remove(position: Int) {
        materials.removeAt(position)
        notifyItemRemoved(position)
    }

    fun updateAll(list: List<Material>) {
        notifyItemRangeRemoved(0, materials.size)
        materials.clear()
        materials.addAll(list)
        notifyItemRangeInserted(0, materials.size)
    }
}