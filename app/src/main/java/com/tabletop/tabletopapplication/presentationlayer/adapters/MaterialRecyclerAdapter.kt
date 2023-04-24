package com.tabletop.tabletopapplication.presentationlayer.adapters

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
import com.bumptech.glide.Glide
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.InstallMaterialActivity
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.Dice
import com.tabletop.tabletopapplication.presentationlayer.models.Material.Material
import com.tabletop.tabletopapplication.presentationlayer.models.Note.Note
import com.tabletop.tabletopapplication.presentationlayer.models.Timer.Timer
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.TimerDBViewModel

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

            val resourceId = itemView.context.resources.getIdentifier(material.image, "drawable", itemView.context.packageName)


            Glide.with(image)
                .load(resourceId)
                .error(R.drawable.black_rectangle)
                .into(image)

            itemView.findViewById<LinearLayout>(R.id.card_material__material).setOnClickListener {
                if (isChooseMaterial) {
                    sendID(material,noteViewModel,diceViewModel,timerViewModel,gameId)
                    transition(material)
                }
            }

            when(itemView.context) {
                is InstallMaterialActivity -> {
                    val installButton=itemView.findViewById<CardView>(R.id.card_material__install_button)
                    installButton.isVisible = true
                    installButton.setOnClickListener {
                        val splitInstallManager = SplitInstallManagerFactory.create(itemView.context)
                        val moduleName = when(material.id){
                            1L-> ":dice"
                            2L-> ":note"
                            3L-> ":timer"
                            //"sandTImer"->listOfMaterials.add(data[3])
                            else -> "Error"
                        }
                        if (moduleName!="Error")
                        {
                            Log.i("AAAAA","AAAAA")
                            val request = SplitInstallRequest.newBuilder().addModule(moduleName).build()
                            splitInstallManager
                                .startInstall(request)
                                .addOnSuccessListener { sessionId -> transition(material) }
                                .addOnFailureListener { exception ->  Log.i("ErrorMy",exception.toString())}
                        }




                    }
                }
            }
        }

        private fun transition(material: Material):Intent
        {
            val intent = Intent(itemView.context, GameEditActivity::class.java)
            intent.putExtra("typeMaterial", material.id)
            return intent
        }

        private fun sendID(
            material: Material,
            noteViewModel: NoteViewModel,
            diceViewModel: DiceDBViewModel,
            timerViewModel: TimerDBViewModel,
            gameId: Long
        ) {

            when (material.id) {
                1L -> {
                    val typeMaterial = Dice(gameId)
                    diceViewModel.addDice(typeMaterial)
                }
                2L->{
                    val typeMaterial = Note("", gameId)
                    noteViewModel.addNote(typeMaterial)
                }
                3L->{
                    val typeMaterial = Timer(gameId)
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