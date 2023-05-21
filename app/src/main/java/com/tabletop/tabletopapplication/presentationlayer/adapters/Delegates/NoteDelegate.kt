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

import com.tabletop.tabletopapplication.presentationlayer.adapters.DelegateMaterialsAdapter
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.EntityROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel

class NoteDelegate(val adapter: DelegateMaterialsAdapter):AdapterDelegate<ArrayList<EntityROOM>>() {

    class NoteViewHolder(val parent: ViewGroup) :
        RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.note_card,parent,false))
    {
        fun bind(item: MaterialROOM, adapter: DelegateMaterialsAdapter, position: Int)
        {
            val note= itemView.findViewById<EditText>(R.id.editTextMini)
             when(itemView.context) {
               is GamePreviewActivity -> {
                   note.setText(item.description)
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
                       adapter.remove(position)
                   }
               }
           }


        }
    }

    override fun isForViewType(items: ArrayList<EntityROOM>, position: Int): Boolean {
        return items[position] is MaterialROOM
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
        (holder as NoteViewHolder).bind(items[position] as MaterialROOM,adapter,position)
    }
}