package com.example.hourglass

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.example.hourglass.activities.HourglassActivity
import com.tabletop.tabletopapplication.presentationlayer.models.Material

class Delegate : AdapterDelegate<ArrayList<Material>>() {

    class HourglassViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val hourglass = itemView.findViewById<CardView>(R.id.hourglass_card_mini)

        fun bind(item: Material) {
            when (itemView.context) {
                is GamePreviewActivity -> {
                    itemView.setOnClickListener {
                        hourglass.setOnClickListener {
                            val intent = Intent(itemView.context, HourglassActivity::class.java)
                            itemView.context.startActivity(intent)
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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourglass_card, parent, false)
        return HourglassViewHolder(view)
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