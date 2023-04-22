package com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.presentationlayer.activities.DiceSettingsActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.Dice
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel

class DiceDelegate(val adapter: ModelAdapter, val diceDBViewModel: DiceDBViewModel): AdapterDelegate<ArrayList<Model>>() {
    class DiceViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dice_card,parent,false))
    {
        fun bind(item: Dice, adapter: ModelAdapter, position: Int, diceDBViewModel: DiceDBViewModel)
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

    override fun isForViewType(items: ArrayList<Model>, position: Int): Boolean {
        return items[position] is Dice
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return DiceViewHolder(parent)
    }

    override fun onBindViewHolder(
        items: ArrayList<Model>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as DiceViewHolder).bind(items[position] as Dice,adapter,position,diceDBViewModel)
    }
}