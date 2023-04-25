package com.tabletop.tabletopapplication.presentationlayer.adapters.Delegates

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity

import com.tabletop.tabletopapplication.presentationlayer.adapters.ModelAdapter
import com.tabletop.tabletopapplication.presentationlayer.models.Model
import com.tabletop.tabletopapplication.presentationlayer.models.Note.Note
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel

class NoteDelegate(val adapter: ModelAdapter, val noteViewModel: GameDBViewModel):AdapterDelegate<ArrayList<Model>>() {

    class NoteViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_card,parent,false))
    {
        fun bind(item: Note, adapter: ModelAdapter, position: Int, noteViewModel: GameDBViewModel)
        {
            val note= itemView.findViewById<EditText>(R.id.editTextMini)
             when(itemView.context) {
               is GamePreviewActivity -> {
                   note.setText(item.noteDescription)
                   note.setOnClickListener {

                       val manager = SplitInstallManagerFactory.create(parent.context)
                       if (manager.installedModules.contains("note")) {
                           // загружаем класс из модуля
                           val myClass = Class.forName("com.tabletop.note.NoteActivity")
                           // создаем экземпляр класса
                           val instance = myClass.newInstance()
                           // вызываем метод класса
                           val intent = Intent(parent.context, instance::class.java)
                           intent.putExtra("idnote",item.id)
                           parent.context.startActivity(intent)
                       }
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