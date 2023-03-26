package com.example.tabletopapplication.presentationlayer.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.activities.EditGameActivity
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.NoteViewModel

class MaterialRecyclerAdapter(
    private val materials: List<Material>,
    private val noteViewModel: NoteViewModel,
    private val editMode: Boolean = false,
    private val isChooseMaterial:Boolean = false
) : RecyclerView.Adapter<MaterialRecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,

    ) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.card_material__name)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.card_material__description)
        private val image: ImageView = itemView.findViewById(R.id.card_material__image)
        private val deleteButton: CardView = itemView.findViewById(R.id.card_material__delete_button)
        private val cardMaterial:LinearLayout = itemView.findViewById(R.id.card_material_all)

        fun bind(material: Material, editMode: Boolean, isChooseMaterial: Boolean,noteViewModel: NoteViewModel) {
            nameTextView.text = material.name
            descriptionTextView.text = material.description
            image.setImageResource(R.drawable.notes)
            deleteButton.isVisible = editMode

            deleteButton.setOnClickListener {
                //adapter.removeMaterial(material)
            }

            val context = itemView.context
            cardMaterial.setOnClickListener {
                if (isChooseMaterial) {
                    val intent = Intent(context, EditGameActivity::class.java)
                    sendID(material,noteViewModel,context,intent)

                }
            }

        }

        private fun sendID(
            material: Material,
            viewModel: NoteViewModel,
            context: Context,
            intent: Intent,
        ) {
            when (material.id) {
                1 -> {
                    val typeMaterial = Note("")
                    Log.i("AAA",material.id.toString() + "A")
                    //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    intent.putExtra("idMaterial", typeMaterial.id)
                    intent.putExtra("typoMaterial", material.id)
                    viewModel.addNote(typeMaterial)
                    context.startActivity(intent)
                }
                else -> {}
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_material, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return materials.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(materials[position], editMode,isChooseMaterial,noteViewModel)
    }

    /*fun addMaterial(material: Material) {
        materials.add(material)
        notifyItemChanged(materials.size - 1)
    }

    fun addListMaterials(materials: List<Material>) {
        this.materials.addAll(materials)
        notifyItemRangeChanged(this.materials.size - materials.size, materials.size)
    }

    fun removeMaterialAt(position: Int) {
        materials.removeAt(position)
        notifyItemRemoved(position)
    }

    fun removeMaterial(material: Material) {
        val position = materials.indexOf(material)
        removeMaterialAt(position)
    }*/
}