package com.example.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.presentationlayer.adapters.DelegateMaterialsAdapter
import com.tabletop.tabletopapplication.presentationlayer.common.AdapterMode
import com.tabletop.tabletopapplication.presentationlayer.models.Material

class Delegate(
    private val adapter: DelegateMaterialsAdapter
): AdapterDelegate<ArrayList<Material>>() {

    class ViewHolder(
        val adapter: DelegateMaterialsAdapter,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        private val note = itemView.findViewById<EditText>(R.id.editTextMini)

        fun bind(position: Int) {
            when (adapter.mode) {
                AdapterMode.PREVIEW -> {

                }
                AdapterMode.EDIT -> {
                    itemView.findViewById<CardView>(R.id.edit_text_delete).apply {
                        isVisible = true
                        setOnClickListener {
                            adapter.remove(position)
                        }
                    }
                }
            }
        }
    }

    override fun isForViewType(items: ArrayList<Material>, position: Int): Boolean {
        return items[position].name == "note"
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_card, parent, false)
        return ViewHolder(adapter, view)
    }

    override fun onBindViewHolder(
        items: ArrayList<Material>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as ViewHolder).bind(position)
    }
}