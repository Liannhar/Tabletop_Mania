package com.example.dice

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.dice.activities.DiceActivity
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.example.dice.activities.DiceSettingsActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.models.Material

class Delegate(): AdapterDelegate<ArrayList<Material>>() {
    class DiceViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        private val dice = itemView.findViewById<CardView>(R.id.dice_card_mini)

        fun bind(material: Material, position: Int)
        {
            when(itemView.context) {
                is GamePreviewActivity -> {
                    itemView.setOnClickListener {
                        dice.setOnClickListener {
                            val intent = Intent(itemView.context, DiceActivity::class.java)
                            intent.putExtra("doQuit", true)
                            itemView.context.startActivity(intent)
                        }
                    }
                }
                is GameEditActivity -> {
                    val deleteButton=itemView.findViewById<CardView>(R.id.dice_card_delete)
                    deleteButton.isVisible = true

                }
            }
        }
    }

    override fun isForViewType(items: ArrayList<Material>, position: Int): Boolean {
        return items[position].name == "dice"
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dice_card,parent,false)
        return DiceViewHolder(view)
    }

    override fun onBindViewHolder(
        items: ArrayList<Material>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {

        (holder as DiceViewHolder).bind(items[position], position)
    }
}