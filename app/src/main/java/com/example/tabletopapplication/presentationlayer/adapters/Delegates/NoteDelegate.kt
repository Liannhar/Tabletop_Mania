package com.example.tabletopapplication.presentationlayer.adapters.Delegates

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.example.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.example.tabletopapplication.presentationlayer.models.Model
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.activities.NoteActivity
import com.example.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.example.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class NoteDelegate(val adapter: ModelAdapter,val noteViewModel: NoteViewModel):AdapterDelegate<ArrayList<Model>>() {

    class NoteViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_card,parent,false))
    {
        fun bind(item: Note,adapter: ModelAdapter,position: Int,noteViewModel: NoteViewModel)
        {
            val note= itemView.findViewById<EditText>(R.id.editTextMini)


             when(itemView.context) {
               is GamePreviewActivity -> {
                   note.setText(item.noteDescription)
                   note.setOnClickListener {
                       val intent = Intent(parent.context, NoteActivity::class.java)
                       parent.context.startActivity(intent)
                   }
               }
               is GameEditActivity -> {
                   val deleteButton=itemView.findViewById<CardView>(R.id.edit_text_delete)
                   deleteButton.isVisible = true
                   deleteButton.setOnClickListener {
                       adapter.removeItem(position)
                       noteViewModel.deleteNote(item)
                   }
               }
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
        (holder as NoteViewHolder).bind(items[position] as Note,adapter,position,noteViewModel)
    }
}