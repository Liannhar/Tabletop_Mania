package com.tabletop.tabletopapplication.presentationlayer.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.ChooseMaterialActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.InstallMaterialActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.MySplitInstallStateUpdatedListener
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.DiceROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.HourglassROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.NoteROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.TimerROOM
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.GameROOM

import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MaterialRecyclerAdapter(
    private val materialROOMS: MutableList<MaterialROOM>,
    private val gameDBViewModel: GameDBViewModel,
    private val gameId: Int,
) : RecyclerView.Adapter<MaterialRecyclerAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView: TextView = itemView.findViewById(R.id.card_material__name)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.card_material__description)
        private val image: ImageView = itemView.findViewById(R.id.card_material__image)

        fun bind(materialROOM: MaterialROOM, gameDBViewModel: GameDBViewModel, gameId: Int) {
            nameTextView.text = materialROOM.name
            descriptionTextView.text = materialROOM.description

            val resourceId = itemView.context.resources.getIdentifier(
                materialROOM.image,
                "drawable",
                itemView.context.packageName
            )

            Glide.with(image)
                .load(resourceId)
                .error(R.drawable.black_rectangle)
                .into(image)

            when (itemView.context) {
                is InstallMaterialActivity -> {
                    val installButton = itemView.findViewById<CardView>(R.id.card_material__install_button)
                    installButton.isVisible = true

                    installButton.setOnClickListener {
                        val splitInstallManager = SplitInstallManagerFactory.create(itemView.context)

                        val moduleName = when (materialROOM.id) {
                            1 -> "dice"
                            2 -> "note"
                            3 -> "timer"
                            4 -> "hourglass"
                            else -> "Error"
                        }

                        if (moduleName != "Error") {
                            var mySessionId = 0
                            val listener = MySplitInstallStateUpdatedListener(itemView)
                            splitInstallManager.registerListener(listener)

                            val request = SplitInstallRequest.newBuilder().addModule(moduleName).build()
                            splitInstallManager
                                .startInstall(request)
                                .addOnSuccessListener { sessionId ->
                                    mySessionId = sessionId
                                    Toast.makeText(
                                        itemView.context,
                                        "Module installation started",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(
                                        itemView.context,
                                        "Module installation failed: $exception",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            splitInstallManager.unregisterListener(listener)
                        }
                    }
                }
                is ChooseMaterialActivity -> {
                    itemView.findViewById<LinearLayout>(R.id.card_material__material)
                        .setOnClickListener {
                            (itemView.context as ChooseMaterialActivity).lifecycleScope.launch() {
                                val game = gameDBViewModel.getGame(gameId)?.let {
                                    sendID(materialROOM, gameDBViewModel, gameId, GameROOM(it))
                                    gameDBViewModel.updateGame(GameROOM(it))
                                }
                            }
                            val intent = Intent(itemView.context, GameEditActivity::class.java)
                            startActivity(itemView.context, intent, null)
                        }
                }

            }
        }


        private fun sendID(
            materialROOM: MaterialROOM,
            gameDBViewModel: GameDBViewModel,
            gameId: Int,
            gameROOM: GameROOM
        ) {

//            when (materialROOM.id) {
//                1L -> {
//                    val typeMaterial = DiceROOM(gameId, positionAdd = gameROOM.count)
//                    gameDBViewModel.addDice(typeMaterial)
//                }
//                2L -> {
//                    val typeMaterial = NoteROOM("", gameId, positionAdd = gameROOM.count)
//                    gameDBViewModel.addNote(typeMaterial)
//                }
//                3L -> {
//                    val typeMaterial = TimerROOM(gameId, positionAdd = gameROOM.count)
//                    gameDBViewModel.addTimer(typeMaterial)
//                }
//                4L -> {
//                    val typeMaterial = HourglassROOM(gameId, positionAdd = gameROOM.count)
//                    gameDBViewModel.addHourglass(typeMaterial)
//                }
//                else -> {}
//            }

        }
    }

    fun updateData(newListOfMaterialROOMS: List<MaterialROOM>) {
        materialROOMS.clear()
        materialROOMS.addAll(newListOfMaterialROOMS)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_material, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return materialROOMS.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(materialROOMS[position], gameDBViewModel, gameId)
    }
}