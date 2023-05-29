package com.example.timer

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.doOnAttach
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.timer.fragments.TimerFragment
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.presentationlayer.adapters.DelegateMaterialsAdapter
import com.tabletop.tabletopapplication.presentationlayer.common.AdapterMode
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import kotlinx.coroutines.launch


class Delegate(
    private val adapter: DelegateMaterialsAdapter
) : AdapterDelegate<ArrayList<Material>>() {

    class TimerViewHolder(
        val adapter: DelegateMaterialsAdapter,
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        val cwContainer = itemView.findViewById<CardView>(R.id.timer_card_mini).apply {
            id = View.generateViewId()
        }

        fun bind(position: Int) {
            when(adapter.mode) {
                AdapterMode.PREVIEW -> {
                    itemView.doOnAttach {
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
                AdapterMode.EDIT -> {
                    itemView.findViewById<CardView>(R.id.timer_card_delete).apply {
                        isVisible = true
                        setOnClickListener {
                            adapter.remove(position)
                        }
                    }
                }
            }
        }
    }

    override fun isForViewType(items: ArrayList<Material>, position: Int): Boolean {
        return items[position].name == "timer"
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.timer_host_layout, parent, false)
        return TimerViewHolder(adapter, view)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        super.onViewAttachedToWindow(holder)

        (holder as TimerViewHolder).apply {

        }
    }

    override fun onBindViewHolder(
        items: ArrayList<Material>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as TimerViewHolder).bind(position)
    }
}
