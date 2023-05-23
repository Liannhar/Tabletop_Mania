package com.example.hourglass

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.example.hourglass.activities.HourglassActivity
import com.tabletop.tabletopapplication.presentationlayer.models.Material

class Delegate: AdapterDelegate<ArrayList<Material>>() {

    class HourglassViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.hourglass_card, parent, false)
        ) {

        fun bind(item: Material) {
            val hourglass = itemView.findViewById<CardView>(R.id.hourglass_card_mini)

            when (itemView.context) {
                is GamePreviewActivity -> {
                    itemView.setOnClickListener {
                        hourglass.setOnClickListener {
                            val intent = Intent(parent.context, HourglassActivity::class.java)
                            parent.context.startActivity(intent)
                        }
                    }
                }
                is GameEditActivity -> {
                    val deleteButton = itemView.findViewById<CardView>(R.id.hourglass_card_delete)
                    deleteButton.isVisible = true
//                    deleteButton.setOnClickListener {
//                        adapter.remove(position)
//                    }
                }
            }
        }
    }

    override fun isForViewType(items: ArrayList<Material>, position: Int): Boolean {
        return items[position].name == "hourglass"
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return HourglassViewHolder(parent)
    }

    override fun onBindViewHolder(
        items: ArrayList<Material>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as HourglassViewHolder).bind(items[position])
    }
}