package com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.fragments.TimerFragment
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.models.Timer.Timer
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.TimerDBViewModel

class TimerDelegate(val contextt:FragmentActivity,val timerViewModel: TimerDBViewModel): AdapterDelegate<ArrayList<Model>>() {

    class TimerViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.timer_host_layout,parent,false))
    {
        fun bind(item: Timer, contextt: FragmentActivity, timerViewModel: TimerDBViewModel)
        {
            contextt.supportFragmentManager.let {
                val transaction = it.beginTransaction()
                transaction.add(R.id.timer_host, TimerFragment.newInstance(), TimerFragment.TAG)
                transaction.commit()
            }


             when(itemView.context) {
               is GamePreviewActivity -> {
                   itemView.setOnClickListener {
                   }
               }
               is GameEditActivity -> {

               }
           }
        }
    }

    override fun isForViewType(items: ArrayList<Model>, position: Int): Boolean {
        return items[position] is Timer
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return TimerViewHolder(parent)
    }

    override fun onBindViewHolder(
        items: ArrayList<Model>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as TimerViewHolder).bind(items[position] as Timer,contextt, timerViewModel)
    }
}