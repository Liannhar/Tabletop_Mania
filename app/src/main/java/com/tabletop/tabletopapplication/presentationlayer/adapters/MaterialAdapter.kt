package com.tabletop.tabletopapplication.presentationlayer.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.service.autofill.OnClickAction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.models.Material
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.launch

class MaterialAdapter(
    private val context: Context,
    private val materials: ArrayList<Material> = arrayListOf(),
    private val mode: Mode = Mode.PREVIEW,
    private val databaseVM: DBViewModel = DBViewModel((context as Activity).application)
) : RecyclerView.Adapter<MaterialAdapter.ViewHolder>() {

    enum class Mode {
        PREVIEW,
        INSTALL
    }

    private val splitInstallManager = SplitInstallManagerFactory.create(context).apply {
        registerListener {
            when(it.status()) {

                SplitInstallSessionStatus.INSTALLED -> {
                    Toast.makeText(context, "INSTALLED", Toast.LENGTH_LONG).show()
                }

                else -> {

                }
            }
        }
    }

    class ViewHolder(
        itemView: View,
        private val splitInstallManager: SplitInstallManager,
        private val mode: Mode,
        private val databaseVM: DBViewModel
    ) : RecyclerView.ViewHolder(itemView) {

        private val nameTextView = itemView.findViewById<TextView>(R.id.card_material__name)
        private val descriptionTextView = itemView.findViewById<TextView>(R.id.card_material__description)
        private val image = itemView.findViewById<ImageView>(R.id.card_material__image)

        private val installButton = itemView.findViewById<CardView>(R.id.card_material__install_button)
        private val deleteButton = itemView.findViewById<CardView>(R.id.card_material__delete_button)

        fun bind(material: Material) {
            nameTextView.text = material.name
            descriptionTextView.text = material.description

//            val resourceId = itemView.context.resources.getIdentifier(
//                materialROOM.image,
//                "drawable",
//                itemView.context.packageName
//            )
//            Glide.with(image)
//                .load(resourceId)
//                .into(image)

            Glide.with(image)
                .load(material.image)
                .centerCrop()
                .placeholder(R.drawable.baseline_downloading_24)
                .error(R.drawable.baseline_error_outline_24)
                .into(image)

            when (mode) {
                Mode.PREVIEW -> {
                    itemView.setOnClickListener {
                        (it.context as Activity).apply {
                            setResult(Activity.RESULT_OK, Intent().apply {
                                putExtra("Material", material)
                            })
                            finish()
                        }
                    }
                }
                Mode.INSTALL -> {
                    databaseVM.viewModelScope.launch {
                        if (databaseVM.getMaterial(material.name) != null)
                            deleteButton.isVisible = true
                        else
                            installButton.isVisible = true

                        deleteButton.setOnClickListener {
                            splitInstallManager
                                .deferredUninstall(listOf(material.name))
                                .addOnSuccessListener {
                                    databaseVM.delete(material)

                                    deleteButton.isVisible = false
                                    installButton.isVisible = true
                                }
                        }

                        installButton.setOnClickListener {
                            splitInstallManager
                                .startInstall(SplitInstallRequest
                                    .newBuilder()
                                    .addModule(material.name)
                                    .build())
                                .addOnSuccessListener {
                                    databaseVM.add(material)

                                    installButton.isVisible = false
                                    deleteButton.isVisible = true
                                }
                        }
                    }
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.card_material, parent, false)
        return ViewHolder(itemView, splitInstallManager, mode, databaseVM)
    }

    override fun getItemCount(): Int {
        return materials.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(materials[position])
    }

    fun addAll(list: List<Material>) {
        materials.addAll(list)
        notifyItemRangeChanged(materials.size - list.size, list.size)
    }

    fun updateAll(list: List<Material>) {
        notifyItemRangeRemoved(0, materials.size)
        materials.clear()
        materials.addAll(list)
        notifyItemRangeInserted(0, materials.size)
    }

    fun getDownloadedMaterials() = materials.filter { material ->
        material.name in splitInstallManager.installedModules
    }
}
