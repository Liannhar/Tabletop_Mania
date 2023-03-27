package com.example.tabletopapplication.presentationlayer.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.example.tabletopapplication.presentationlayer.models.DIce.Dice
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.models.Note.Note
import com.example.tabletopapplication.presentationlayer.models.Timer.Timer
import com.example.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.TimerDBViewModel

class MaterialRecyclerAdapter(
    private val materials: List<Material>,
    private val noteViewModel: NoteViewModel,
    private val diceViewModel: DiceDBViewModel,
    private val timerViewModel: TimerDBViewModel,
    private val isChooseMaterial:Boolean = false
) : RecyclerView.Adapter<MaterialRecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,
        private val adapter: MaterialRecyclerAdapter
    ) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.card_material__name)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.card_material__description)
        private val image: ImageView = itemView.findViewById(R.id.card_material__image)

        fun bind(material: Material,isChooseMaterial: Boolean,noteViewModel: NoteViewModel,diceViewModel: DiceDBViewModel, timerViewModel: TimerDBViewModel,){
            nameTextView.text = material.name
            descriptionTextView.text = material.description

            Glide.with(image)
                .load(material.image)
                .error(R.drawable.black_rectangle)
                .into(image)
            val context = itemView.context

            itemView.findViewById<LinearLayout>(R.id.card_material__material).setOnClickListener {
                if (isChooseMaterial) {
                    val intent = Intent(context, GameEditActivity::class.java)
                    sendID(material,noteViewModel,diceViewModel,timerViewModel,context,intent)

                }
            }
        }

        private fun sendID(
            material: Material,
            noteViewModel: NoteViewModel,
            diceViewModel: DiceDBViewModel,
            timerViewModel: TimerDBViewModel,
            context: Context,
            intent: Intent,
        ) {

            when (material.id) {
                1 -> {
                    val typeMaterial = Note("")
                    //intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT)
                    intent.putExtra("idMaterial", material.id)

                    noteViewModel.addNote(typeMaterial)
                    context.startActivity(intent)
                }
                2->{
                    val typeMaterial = Dice()
                    intent.putExtra("idMaterial", material.id)
                    diceViewModel.addDice(typeMaterial)
                    context.startActivity(intent)
                }
                3->{
                    val typeMaterial = Timer()
                    intent.putExtra("idMaterial", material.id)
                    timerViewModel.addTimer(typeMaterial)
                    context.startActivity(intent)
                }
                else -> {}
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
        holder.bind(materials[position],isChooseMaterial,noteViewModel, diceViewModel, timerViewModel)
    }

    /*fun bind(item: MaterialEntity) {
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
}*/
}