package com.example.tabletopapplication.presentationlayer.adapters.Delegates

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.activities.NoteActivity
import com.example.tabletopapplication.presentationlayer.fragments.TimerFragment
import com.example.tabletopapplication.presentationlayer.models.Model
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.models.Timer.Timer
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class TimerDelegate(val editMode:Boolean): AdapterDelegate<ArrayList<Model>>() {

    class TimerViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.set_time_fragment,parent,false))
    {
        fun bind(item: Timer, editMode:Boolean)
        {

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
        (holder as TimerViewHolder).bind(items[position] as Timer,editMode)
    }
}