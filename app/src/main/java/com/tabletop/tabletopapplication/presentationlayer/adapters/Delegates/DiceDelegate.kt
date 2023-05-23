package com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.DiceActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.DiceConstants
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.tabletop.tabletopapplication.presentationlayer.fragments.DiceFragment
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.Dice
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel

class DiceDelegate(val adapter: ModelAdapter, private val contextt:FragmentActivity, private val diceDBViewModel: GameDBViewModel): AdapterDelegate<ArrayList<Model>>() {
    class DiceViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.dice_card,parent,false))
    {
        fun bind(item: Dice, position: Int, contextt: FragmentActivity, diceDBViewModel: GameDBViewModel, adapter: ModelAdapter)
        {

            when(itemView.context) {

                is GamePreviewActivity -> {
                    /*contextt.supportFragmentManager.let {
                        val transaction = it.beginTransaction()
                        transaction.add(R.id.dice_card_mini, DiceFragment.newInstance(), DiceFragment.TAG)
                        // transaction.replace(R.id.dice_card_mini, DiceFragment.newInstance(), DiceFragment.TAG)
                        transaction.commit()
                    }*/

                    itemView.setOnClickListener {
                        val intent = Intent(itemView.context, DiceActivity::class.java)
                        intent.putExtra("doQuit", true)
                        startActivity(itemView.context,intent,null)
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
        (holder as DiceViewHolder).bind(items[position] as Dice, position, contextt, diceDBViewModel, adapter)
    }
}