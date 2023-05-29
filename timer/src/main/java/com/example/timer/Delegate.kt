package com.example.timer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.fragments.TimerFragment
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import kotlinx.coroutines.launch


class Delegate : AdapterDelegate<ArrayList<Material>>() {

    class TimerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val fragmentManager = (itemView.context as AppCompatActivity).supportFragmentManager
        val cwContainer = itemView.findViewById<CardView>(R.id.timer_card_mini).apply {
            id = View.generateViewId()
        }

        fun bind() {

        }
    }

    override fun isForViewType(items: ArrayList<Material>, position: Int): Boolean {
        return items[position].name == "timer"
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_host_layout, parent, false)
        return TimerViewHolder(view)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)

        (holder as TimerViewHolder).apply {
            try {
                (itemView.context as AppCompatActivity).apply {
                    lifecycleScope.launch {
                        supportFragmentManager
                            .beginTransaction()
                            .add(cwContainer.id, TimerFragment().apply {
                                arguments = Bundle().apply {
                                    putInt("viewId", cwContainer.id)
                                }
                            })
                            .commit()
                    }
                }
            } catch (e: Exception) {
                Log.d("ERROR", e.toString())
            }
        }
    }

    override fun onBindViewHolder(
        items: ArrayList<Material>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as TimerViewHolder).bind()
    }
}
