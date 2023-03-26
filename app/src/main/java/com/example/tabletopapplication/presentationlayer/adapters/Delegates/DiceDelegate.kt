package com.example.tabletopapplication.presentationlayer.adapters.Delegates

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.activities.DiceSettingsActivity
import com.example.tabletopapplication.presentationlayer.models.DIce.Dice
import com.example.tabletopapplication.presentationlayer.models.Model
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class DiceDelegate(val editMode:Boolean): AdapterDelegate<ArrayList<Model>>() {
    class DiceViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dice_card,parent,false))
    {
        fun bind(item: Dice, editMode:Boolean)
        {
            val dice= itemView.findViewById<EditText>(R.id.dice_card_mini)

            dice.setOnClickListener {
                val intent = Intent(parent.context, DiceSettingsActivity::class.java)
                parent.context.startActivity(intent)
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
        (holder as DiceViewHolder).bind(items[position] as Dice,editMode)
    }
}