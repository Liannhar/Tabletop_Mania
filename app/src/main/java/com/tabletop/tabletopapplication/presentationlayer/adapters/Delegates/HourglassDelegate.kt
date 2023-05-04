package com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates

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
import com.tabletop.tabletopapplication.presentationlayer.activities.HourglassActivity
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialsAdapter
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.HourglassROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.EntityROOM
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel

class HourglassDelegate(val adapter: MaterialsAdapter, private val hourglassDBViewModel: GameDBViewModel): AdapterDelegate<ArrayList<EntityROOM>>() {
    class HourglassViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.hourglass_card,parent,false))
    {
        fun bind(item: HourglassROOM, adapter: MaterialsAdapter, position: Int, hourglassDBViewModel: GameDBViewModel)
        {
            val hourglass= itemView.findViewById<CardView>(R.id.hourglass_card_mini)

            when(itemView.context) {
                is GamePreviewActivity -> {
                    itemView.setOnClickListener {
                        hourglass.setOnClickListener {
                            val intent = Intent(parent.context, HourglassActivity::class.java)
                            parent.context.startActivity(intent)
                        }
                    }
                }
                is GameEditActivity -> {
                    val deleteButton=itemView.findViewById<CardView>(R.id.hourglass_card_delete)
                    deleteButton.isVisible = true
                    deleteButton.setOnClickListener {
                        adapter.removeItem(position)
                        hourglassDBViewModel.deleteHourglass(item)
                    }
                }
            }


        }
    }

    override fun isForViewType(items: ArrayList<EntityROOM>, position: Int): Boolean {
        return items[position] is HourglassROOM
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return HourglassViewHolder(parent)
    }

    override fun onBindViewHolder(
        items: ArrayList<EntityROOM>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as HourglassViewHolder).bind(items[position] as HourglassROOM,adapter,position,hourglassDBViewModel)
    }
}