package com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.DiceSettingsActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialsAdapter
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.DiceROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.EntityROOM
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel

class DiceDelegate(val adapter: MaterialsAdapter, private val diceDBViewModel: GameDBViewModel): AdapterDelegate<ArrayList<EntityROOM>>() {
    class DiceViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dice_card,parent,false))
    {
        fun bind(item: DiceROOM, adapter: MaterialsAdapter, position: Int, diceDBViewModel: GameDBViewModel)
        {
            val dice= itemView.findViewById<CardView>(R.id.dice_card_mini)

            when(itemView.context) {
                is GamePreviewActivity -> {
                    itemView.setOnClickListener {
                        dice.setOnClickListener {
                            val intent = Intent(parent.context, DiceSettingsActivity::class.java)
                            parent.context.startActivity(intent)
                        }
                    }
                }
                is GameEditActivity -> {
                    val deleteButton=itemView.findViewById<CardView>(R.id.dice_card_delete)
                    deleteButton.isVisible = true
                    deleteButton.setOnClickListener {
                        adapter.removeItem(position)
                        diceDBViewModel.deleteDice(item)
                    }
                }
            }


        }
    }

    override fun isForViewType(items: ArrayList<EntityROOM>, position: Int): Boolean {
        return items[position] is DiceROOM
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return DiceViewHolder(parent)
    }

    override fun onBindViewHolder(
        items: ArrayList<EntityROOM>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as DiceViewHolder).bind(items[position] as DiceROOM,adapter,position,diceDBViewModel)
    }
}