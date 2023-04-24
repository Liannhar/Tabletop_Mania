package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.tabletop.tabletopapplication.presentationlayer.models.Material.Material
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.TimerDBViewModel


class ChooseMaterialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_material)
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)
        val back_button = findViewById<ImageView>(R.id.arrow_back)
        val download_button = findViewById<ImageView>(R.id.download)
        val materialViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MaterialViewModel::class.java]
        val noteViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]
        val diceViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DiceDBViewModel::class.java]
        val timerViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[TimerDBViewModel::class.java]

        val listOfMaterials= mutableListOf<Material>()
        val recyclerView: RecyclerView = findViewById(R.id.rv_choose_material)
        recyclerView.layoutManager = LinearLayoutManager(this)


        val installModules = SplitInstallManagerFactory.create(this).installedModules
        installModules.forEach {

            materialViewModel.getAllMaterials().observe(this){ data ->
                when(it){
                    "dice"->listOfMaterials.add(data[0])
                    "note"->listOfMaterials.add(data[1])
                    "timer"->listOfMaterials.add(data[2])
                    "sandTImer"->listOfMaterials.add(data[3])
                }
            }

        }
        recyclerView.adapter = MaterialRecyclerAdapter(listOfMaterials,noteViewModel,diceViewModel,timerViewModel,true,gameId)



        back_button.setOnClickListener{
            startActivity(Intent(applicationContext, GameEditActivity::class.java))
            prefs.edit().putInt("idmaterial",-1).apply()
            this.finish()
        }
        download_button.setOnClickListener {
            startActivity(Intent(applicationContext,InstallMaterialActivity::class.java))
            prefs.edit().putInt("idmaterial",-1).apply()
            this.finish()
        }
    }
}