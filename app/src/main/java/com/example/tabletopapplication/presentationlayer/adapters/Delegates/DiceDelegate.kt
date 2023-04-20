package com.example.tabletopapplication.presentationlayer.adapters.Delegates

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.activities.DiceSettingsActivity
import com.example.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.example.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.example.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.example.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.example.tabletopapplication.presentationlayer.models.DIce.Dice
import com.example.tabletopapplication.presentationlayer.models.Model
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class DiceDelegate(val adapter:ModelAdapter,val diceDBViewModel: DiceDBViewModel): AdapterDelegate<ArrayList<Model>>() {
    class DiceViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dice_card,parent,false))
    {
        fun bind(item: Dice,adapter: ModelAdapter,position: Int,diceDBViewModel: DiceDBViewModel)
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