package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitcompat.SplitCompat
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class InstallMaterialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_download_materials)
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getInt("currentGameId", -1)
        val backButton = findViewById<ImageView>(R.id.arrow_back)

        val materialViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MaterialViewModel::class.java]
        val gameDBViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[GameDBViewModel::class.java]


        val recyclerView: RecyclerView = findViewById(R.id.rv_download_material)
        recyclerView.layoutManager = LinearLayoutManager(this)

        lifecycle.coroutineScope.launch() {
            materialViewModel.getAllMaterials().collect { data ->
                val m = mutableListOf<MaterialROOM>()
                m.addAll(data)
                recyclerView.adapter = MaterialRecyclerAdapter(m, gameDBViewModel, gameId)
            }
        }

        lifecycleScope.launch() {
            val material = materialViewModel.getAllMaterials().first()
            val m = mutableListOf<MaterialROOM>()
            m.addAll(material)
            recyclerView.adapter = MaterialRecyclerAdapter(m, gameDBViewModel, gameId)
        }

        backButton.setOnClickListener {
            if (gameId == -1) {
                startActivity(Intent(applicationContext, GameSelectionActivity::class.java))
                prefs.edit().putInt("idmaterial", -1).apply()
                this.finish()
            } else {
                startActivity(Intent(applicationContext, ChooseMaterialActivity::class.java))
                prefs.edit().putInt("idmaterial", -1).apply()
                this.finish()
            }

        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}