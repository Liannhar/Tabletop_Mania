package com.example.tabletopapplication.presentationlayer.adapters.Delegates

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.activities.NoteActivity
import com.example.tabletopapplication.presentationlayer.fragments.TimerFragment
import com.example.tabletopapplication.presentationlayer.models.Model
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.models.Timer.Timer
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class TimerDelegate(val contextt:FragmentActivity): AdapterDelegate<ArrayList<Model>>() {

    class TimerViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.timer_host_layout,parent,false))
    {
        fun bind(item: Timer,contextt: FragmentActivity)
        {
            contextt.supportFragmentManager.let {
                val transaction = it.beginTransaction()
                transaction.add(R.id.timer_host, TimerFragment.newInstance(), TimerFragment.TAG)
                transaction.commit()
            }


            /* when(itemView.context) {
               is GamePreviewActivity -> {
                   itemView.setOnClickListener {
                       // TODO переход на материал
                   }
               }
               is GameEditActivity -> {
                   deleteButton.isVisible = true
                   deleteButton.setOnClickListener {
                       adapter.removeMaterial(item)
                   }
               }
           }*/
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
        (holder as TimerViewHolder).bind(items[position] as Timer,contextt)
    }
}