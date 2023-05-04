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

import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialsAdapter
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.EntityROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.NoteROOM
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel

class NoteDelegate(val adapter: MaterialsAdapter, val noteViewModel: GameDBViewModel):AdapterDelegate<ArrayList<EntityROOM>>() {

    class NoteViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_card,parent,false))
    {
        fun bind(item: NoteROOM, adapter: MaterialsAdapter, position: Int, noteViewModel: GameDBViewModel)
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

    override fun isForViewType(items: ArrayList<EntityROOM>, position: Int): Boolean {
        return items[position] is NoteROOM
    }

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return NoteViewHolder(parent)
    }

    override fun onBindViewHolder(
        items: ArrayList<EntityROOM>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: MutableList<Any>
    ) {
        (holder as NoteViewHolder).bind(items[position] as NoteROOM,adapter,position,noteViewModel)
    }
}