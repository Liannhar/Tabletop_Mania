package com.tabletop.tabletopapplication.presentationlayer.adapters


import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.tabletop.tabletopapplication.presentationlayer.common.AdapterMode
import com.tabletop.tabletopapplication.presentationlayer.models.Material

class DelegateMaterialsAdapter(
    private val context: Context,
    private val materials: ArrayList<Material> = arrayListOf(),
            val mode: AdapterMode = AdapterMode.PREVIEW
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val adapterDelegateManager = AdapterDelegatesManager<ArrayList<Material>>()

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        addListDelegate(materials)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        adapterDelegateManager.onCreateViewHolder(parent, viewType)

    override fun getItemCount(): Int = materials.size

    override fun getItemViewType(position: Int): Int =
        adapterDelegateManager.getItemViewType(materials, position)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        adapterDelegateManager.onBindViewHolder(materials, position, holder)

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) =
        adapterDelegateManager.onViewAttachedToWindow(holder)

    fun getMaterials() = materials

    fun add(item: Material) {

        addDelegate(item)

        materials.add(item)
        notifyItemInserted(materials.size - 1)

    }

    fun addAll(list: List<Material>) {

        addListDelegate(list)

        materials.addAll(list)
        notifyItemRangeInserted(materials.size - list.size, list.size)

    }

    fun remove(position: Int) {

        removeDelegate(materials[position])

        materials.removeAt(position)
        notifyItemRemoved(position)

    }

    fun updateAll(list: List<Material>) {

        removeListDelegate(materials)

        notifyItemRangeRemoved(0, materials.size)
        materials.clear()

        addListDelegate(list)

        materials.addAll(list)
        notifyItemRangeInserted(0, materials.size)

    }

    private fun addDelegate(item: Material) {
        val ref = Class.forName("com.example.${item.name}.Delegate")
        adapterDelegateManager.addDelegate(ref
            .getConstructor(DelegateMaterialsAdapter::class.java)
            .newInstance(this) as AdapterDelegate<ArrayList<Material>>)
    }

    private fun addListDelegate(list: List<Material>) {
        list.forEach { material ->
            addDelegate(material)
        }
    }

    private fun removeDelegate(item: Material) {
        val ref = Class.forName("com.example.${item.name}.Delegate")
        adapterDelegateManager.removeDelegate(ref
            .getConstructor(DelegateMaterialsAdapter::class.java)
            .newInstance(this) as AdapterDelegate<ArrayList<Material>>)
    }

    private fun removeListDelegate(list: List<Material>) {
        list.forEach { material ->
            removeDelegate(material)
        }
    }
}