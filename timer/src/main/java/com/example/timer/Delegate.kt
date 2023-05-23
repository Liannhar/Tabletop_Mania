package com.example.timer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.R
import com.example.timer.fragments.TimerFragment
import com.tabletop.tabletopapplication.presentationlayer.models.Material

class Delegate : AdapterDelegate<ArrayList<Material>>() {

    class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(material: Material) {
            (itemView.context as FragmentActivity).supportFragmentManager.let {
                val transaction = it.beginTransaction()
                transaction.add(
                    R.id.timer_card_mini,
                    TimerFragment.newInstance(),
                    TimerFragment.TAG
                )
                transaction.commit()
            }
        }
    }

    override fun isForViewType(items: ArrayList<Material>, position: Int): Boolean {
        return items[position].name == "timer"
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_host_layout, parent, false)
        return TimerViewHolder(view)
    }

    override fun onBindViewHolder(
        items: ArrayList<Material>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as TimerViewHolder).bind(items[position])
    }
}