package com.example.tabletopapplication.presentationlayer.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.Model
import com.example.tabletopapplication.businesslayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.activities.EditGameActivity
import com.example.tabletopapplication.presentationlayer.activities.NoteActivity
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class NoteDelegate:AdapterDelegate<ArrayList<Model>>() {

    class NoteViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_card,parent,false))
    {
        fun bind(item: Note)
        {
            val note= itemView.findViewById<EditText>(R.id.editTextMini)
            note.setText(item.noteDescription)

            note.setOnClickListener {
                val intent = Intent(parent.context, NoteActivity::class.java)
                intent.putExtra("idnote",item.id )
                parent.context.startActivity(intent)
            }
        }
    }

    override fun isForViewType(items: ArrayList<Model>, position: Int): Boolean {
        return items[position] is Note
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return NoteViewHolder(parent)
    }

    override fun onBindViewHolder(
        items: ArrayList<Model>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as NoteViewHolder).bind(items[position] as Note)
    }
}