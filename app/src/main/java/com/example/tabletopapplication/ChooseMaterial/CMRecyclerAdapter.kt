package com.example.tabletopapplication.ChooseMaterial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabletopapplication.R

class CMRecyclerAdapter(private val listMaterials:List<Materials>):RecyclerView.Adapter<CMRecyclerAdapter.ViewHolder>() {
    class ViewHolder(itemview: View):RecyclerView.ViewHolder(itemview){
        val nameTextView: TextView = itemView.findViewById(R.id.tv_name_text_card_material)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tv_description_text_card_material)
        val image:ImageView=itemview.findViewById(R.id.iv_card_material)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_material, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return listMaterials.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameTextView.text=listMaterials[position].name
        holder.descriptionTextView.text=listMaterials[position].description
        Glide.with(holder.image.context).load(listMaterials[position].image).into(holder.image)
    }
}