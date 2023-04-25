package com.tabletop.tabletopapplication.presentationlayer.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
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
import com.google.android.play.core.splitinstall.SplitInstallSessionState
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.ChooseMaterialActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GameEditActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.InstallMaterialActivity
import com.tabletop.tabletopapplication.presentationlayer.models.DIce.Dice
import com.tabletop.tabletopapplication.presentationlayer.models.Hourglass.Hourglass
import com.tabletop.tabletopapplication.presentationlayer.models.Material.Material
import com.tabletop.tabletopapplication.presentationlayer.models.Note.Note
import com.tabletop.tabletopapplication.presentationlayer.models.Timer.Timer
import com.tabletop.tabletopapplication.presentationlayer.models.game.Game

import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MaterialRecyclerAdapter(
    private val materials: MutableList<Material>,
    private val gameDBViewModel: GameDBViewModel,
    private val gameId:Long,
) : RecyclerView.Adapter<MaterialRecyclerAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,
    ) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.card_material__name)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.card_material__description)
        private val image: ImageView = itemView.findViewById(R.id.card_material__image)

        fun bind(material: Material,gameDBViewModel: GameDBViewModel,gameId: Long){
            nameTextView.text = material.name
            descriptionTextView.text = material.description

            val resourceId = itemView.context.resources.getIdentifier(material.image, "drawable", itemView.context.packageName)

            Glide.with(image)
                .load(resourceId)
                .error(R.drawable.black_rectangle)
                .into(image)

            when(itemView.context) {
                is InstallMaterialActivity -> {
                    val installButton=itemView.findViewById<CardView>(R.id.card_material__install_button)
                    installButton.isVisible = true
                    installButton.setOnClickListener {
                        val splitInstallManager = SplitInstallManagerFactory.create(itemView.context)
                        val moduleName = when(material.id){
                            1L-> "dice"
                            2L-> "note"
                            3L-> "timer"
                            4L-> "hourglass"
                            else -> "Error"
                        }
                        if (moduleName!="Error")
                        {
                            var mySessionId = 0
                            val listener = SplitInstallStateUpdatedListener { state ->
                                Log.i("AAAAAA","123")
                                onStateUpdate(state,mySessionId,itemView)
                            }
                            splitInstallManager.registerListener(listener)

                            val request = SplitInstallRequest.newBuilder().addModule(moduleName).build()
                            splitInstallManager
                                .startInstall(request)
                                .addOnSuccessListener { sessionId -> mySessionId=sessionId
                                    Toast.makeText(itemView.context,
                                    "Module installation started",
                                    Toast.LENGTH_SHORT).show() }
                                .addOnFailureListener { exception ->  Toast.makeText(itemView.context,
                                    "Module installation failed: $exception",
                                    Toast.LENGTH_SHORT).show()}
                            splitInstallManager.unregisterListener(listener)
                        }
                    }
                }
                is ChooseMaterialActivity->{
                    itemView.findViewById<LinearLayout>(R.id.card_material__material).setOnClickListener {
                        (itemView.context as ChooseMaterialActivity).lifecycleScope.launch() {
                            val game = gameDBViewModel.getGame(gameId).first()
                            sendID(material, gameDBViewModel, gameId, game)
                            game.count = game.count + 1
                            gameDBViewModel.updateGame(game)
                        }
                        val intent = Intent(itemView.context,GameEditActivity::class.java)
                        startActivity(itemView.context,intent,null)
                    }

                }

            }
        }

        fun onStateUpdate(state : SplitInstallSessionState, mySessionId:Int,view:View) {
            val progressBar = view.findViewById<ProgressBar>(R.id.pb_horizontal)

            val installButton=itemView.findViewById<CardView>(R.id.card_material__install_button)
            if (state.status() == SplitInstallSessionStatus.FAILED
                && state.errorCode() == SplitInstallErrorCode.SERVICE_DIED) {
                // Retry the request.
                return
            }

            if (state.sessionId() == mySessionId) {
                when (state.status()) {
                    SplitInstallSessionStatus.DOWNLOADING -> {
                        progressBar.isVisible=true
                        val totalBytes = state.totalBytesToDownload()
                        progressBar.max=totalBytes.toInt()
                        val progress = state.bytesDownloaded()
                        progressBar.progress = progress.toInt()
                    }
                    SplitInstallSessionStatus.INSTALLED -> {
                        progressBar.isVisible=false
                        installButton.setBackgroundResource(R.drawable.baseline_check_24)
                        //installButton.setCardBackgroundColor(R.color.green)
                        Toast.makeText(itemView.context,
                            "Module installation finished",
                            Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }


        private fun sendID(
            material: Material,
            gameDBViewModel: GameDBViewModel,
            gameId: Long,
            game: Game
        ) {

            when (material.id) {
                1L -> {
                    val typeMaterial = Dice(gameId, positionAdd = game.count)
                    gameDBViewModel.addDice(typeMaterial)
                }
                2L->{
                    val typeMaterial = Note("", gameId, positionAdd = game.count)
                    gameDBViewModel.addNote(typeMaterial)
                }
                3L->{
                    val typeMaterial = Timer(gameId, positionAdd = game.count)
                    gameDBViewModel.addTimer(typeMaterial)
                }
                4L->{
                    val typeMaterial =Hourglass(gameId, positionAdd = game.count)
                    gameDBViewModel.addHourglass(typeMaterial)
                }
                else -> {}
            }

        }
    }

    fun updateData(newListOfMaterials: List<Material>) {
        materials.clear()
        materials.addAll(newListOfMaterials)
        notifyDataSetChanged()
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
        holder.bind(materials[position],gameDBViewModel,gameId)
    }
}