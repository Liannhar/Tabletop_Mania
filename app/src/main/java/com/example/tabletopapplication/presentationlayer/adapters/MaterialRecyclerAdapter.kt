package com.example.tabletopapplication.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.MaterialEntity
import com.example.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.example.tabletopapplication.presentationlayer.activities.GamePreviewActivity

class MaterialRecyclerAdapter(
    private val materials: ArrayList<MaterialEntity> = arrayListOf()
) : RecyclerView.Adapter<MaterialRecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,
        private val adapter: MaterialRecyclerAdapter
    ) : RecyclerView.ViewHolder(itemView) {
        private val materialView = itemView.findViewById<LinearLayout>(R.id.card_material__material)

        private val name = itemView.findViewById<TextView>(R.id.card_material__name)
        private val description = itemView.findViewById<TextView>(R.id.card_material__description)
        private val image: ImageView = itemView.findViewById(R.id.card_material__image)
        private val deleteButton: CardView = itemView.findViewById(R.id.card_material__delete_button)

        fun bind(item: MaterialEntity) {
            name.text = item.name
            description.text = item.description

            Glide.with(image)
                 .load(item.image_url)
                 .error(R.drawable.baseline_error_outline_24)
                 .into(image)

            when(materialView.context) {
                is GamePreviewActivity -> {
                    materialView.setOnClickListener {
                        // TODO переход на материал
                    }
                }
                is GameEditActivity -> {
                    deleteButton.isVisible = true
                    deleteButton.setOnClickListener {
                        adapter.removeMaterial(item)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_material, parent, false)
        return ViewHolder(itemView, this)
    }

    override fun getItemCount(): Int {
        return materials.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(materials[position])
    }

    fun addMaterial(material: MaterialEntity) {
        materials.add(material)
        notifyItemChanged(materials.size - 1)
    }

    fun addMaterials(materials: List<MaterialEntity>) {
        this.materials.addAll(materials)
        notifyItemRangeChanged(this.materials.size - materials.size, materials.size)
    }

    fun removeMaterialAt(position: Int) {
        materials.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeMaterial(material: MaterialEntity) {
        val position = materials.indexOf(material)
        removeMaterialAt(position)
    }
}