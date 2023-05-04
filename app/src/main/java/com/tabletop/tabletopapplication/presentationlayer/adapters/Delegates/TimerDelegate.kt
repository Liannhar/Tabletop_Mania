package com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialsAdapter
import com.tabletop.tabletopapplication.presentationlayer.fragments.TimerFragment
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.EntityROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.TimerROOM
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel

class TimerDelegate(val adapter: MaterialsAdapter, private val contextt:FragmentActivity, private val timerViewModel: GameDBViewModel): AdapterDelegate<ArrayList<EntityROOM>>() {

    class TimerViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.timer_host_layout,parent,false))
    {
        fun bind(item: TimerROOM, position: Int, contextt: FragmentActivity, timerViewModel: GameDBViewModel, adapter: MaterialsAdapter)
        {
             when(itemView.context) {
               is GamePreviewActivity -> {
                   contextt.supportFragmentManager.let {
                       val transaction = it.beginTransaction()
                       transaction.add(R.id.timer_card_mini, TimerFragment.newInstance(), TimerFragment.TAG)
                       transaction.commit()
                   }
               }
               is GameEditActivity -> {
                   val deleteButton=itemView.findViewById<CardView>(R.id.timer_card_delete)
                   deleteButton.isVisible = true
                   deleteButton.setOnClickListener {
                       adapter.removeItem(position)
                       timerViewModel.deleteTimer(item)
                   }
               }
           }
        }
    }

    override fun isForViewType(items: ArrayList<EntityROOM>, position: Int): Boolean {
        return items[position] is TimerROOM
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TimerViewHolder(parent)
    }

    override fun onBindViewHolder(
        items: ArrayList<EntityROOM>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as TimerViewHolder).bind(items[position] as TimerROOM,position,contextt, timerViewModel,adapter)
    }
}