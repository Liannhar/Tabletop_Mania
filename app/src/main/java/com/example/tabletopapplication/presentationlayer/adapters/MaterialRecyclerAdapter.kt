package com.example.tabletopapplication.presentationlayer.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.example.tabletopapplication.presentationlayer.contract.MyActivityResultContract
import com.example.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
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
    private val isChooseMaterial:Boolean = false,
    private val gameId:Long,
) : RecyclerView.Adapter<MaterialRecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.card_material__name)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.card_material__description)
        private val image: ImageView = itemView.findViewById(R.id.card_material__image)

        fun bind(material: Material,isChooseMaterial: Boolean,noteViewModel: NoteViewModel,diceViewModel: DiceDBViewModel, timerViewModel: TimerDBViewModel,gameId: Long){
            nameTextView.text = material.name
            descriptionTextView.text = material.description

            Glide.with(image)
                .load(material.image)
                .error(R.drawable.black_rectangle)
                .into(image)

            itemView.findViewById<LinearLayout>(R.id.card_material__material).setOnClickListener {
                if (isChooseMaterial) {
                    val intent = Intent(itemView.context, GameEditActivity::class.java)
                    intent.putExtra("typeMaterial", material.id)
                    intent.putExtra("gameId", gameId)
                    sendID(material,noteViewModel,diceViewModel,timerViewModel,gameId,intent)
                    Log.i("AAAAAAA",material.id.toString()+"AAAA")
                    itemView.context.startActivity(intent)
                }
            }
        }

        private fun sendID(
            material: Material,
            noteViewModel: NoteViewModel,
            diceViewModel: DiceDBViewModel,
            timerViewModel: TimerDBViewModel,
            gameId: Long,
            intent:Intent
        ) {

            when (material.id) {
                4L -> {
                    val typeMaterial = Dice(gameId)
                    intent.putExtra("idMaterial", typeMaterial.id)
                    diceViewModel.addDice(typeMaterial)
                }
                5L->{
                    val typeMaterial = Note("", gameId)
                    intent.putExtra("idMaterial", typeMaterial.id)
                    noteViewModel.addNote(typeMaterial)
                }
                6L->{
                    val typeMaterial = Timer(gameId)
                    intent.putExtra("idMaterial", typeMaterial.id)
                    timerViewModel.addTimer(typeMaterial)
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
        holder.bind(materials[position],isChooseMaterial,noteViewModel, diceViewModel, timerViewModel,gameId)
    }
}