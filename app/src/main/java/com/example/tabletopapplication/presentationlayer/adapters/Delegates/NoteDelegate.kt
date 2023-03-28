package com.example.tabletopapplication.presentationlayer.adapters.Delegates

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.models.Model
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.activities.NoteActivity
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class NoteDelegate(val gameId:Long):AdapterDelegate<ArrayList<Model>>() {

    class NoteViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_card,parent,false))
    {
        fun bind(item: Note,gameId: Long)
        {
            val note= itemView.findViewById<EditText>(R.id.editTextMini)
            note.setText(item.noteDescription)
            note.setOnClickListener {
                    val intent = Intent(parent.context, NoteActivity::class.java)
                    intent.putExtra("idnote",item.id )
                    intent.putExtra("gameId",gameId )
                    parent.context.startActivity(intent)
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
        (holder as NoteViewHolder).bind(items[position] as Note,gameId)
    }
}