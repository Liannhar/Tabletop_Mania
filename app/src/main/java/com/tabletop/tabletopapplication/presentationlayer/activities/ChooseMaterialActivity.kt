package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.tabletop.tabletopapplication.businesslayer.ROOM.entities.MaterialROOM
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.GameDBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ChooseMaterialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_material)
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getInt("currentGameId", -1)
        val back_button = findViewById<ImageView>(R.id.arrow_back)
        val download_button = findViewById<ImageView>(R.id.download)
        val materialViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MaterialViewModel::class.java]
        val gameDBViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[GameDBViewModel::class.java]

        val listOfMaterialROOMS= mutableListOf<MaterialROOM>()
        val recyclerView: RecyclerView = findViewById(R.id.rv_choose_material)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val materialAdapter = MaterialRecyclerAdapter(mutableListOf(), gameDBViewModel,gameId)
        recyclerView.adapter = materialAdapter

        val installModules = SplitInstallManagerFactory.create(this).installedModules


        lifecycleScope.launch(){
            val materials =  materialViewModel.getAllMaterials().first()
            installModules.forEach {
                when(it){
                    "dice"->listOfMaterialROOMS.add(materials[0])
                    "note"->listOfMaterialROOMS.add(materials[1])
                    "timer"->listOfMaterialROOMS.add(materials[2])
                    "hourglass"->listOfMaterialROOMS.add(materials[3])
                }

            }
            materialAdapter.updateData(listOfMaterialROOMS)
        }

        back_button.setOnClickListener{
            if (gameId == -1){
                startActivity(Intent(applicationContext, GameSelectionActivity::class.java))
                prefs.edit().putInt("idmaterial", -1).apply()
                this.finish()
            }
            else {
                startActivity(Intent(applicationContext, GameEditActivity::class.java))
                prefs.edit().putInt("idmaterial",-1).apply()
                this.finish()
            }
        }
        download_button.setOnClickListener {
            startActivity(Intent(applicationContext,InstallMaterialActivity::class.java))
            prefs.edit().putInt("idmaterial",-1).apply()
            this.finish()
        }
    }
}