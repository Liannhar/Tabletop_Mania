package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.tabletop.tabletopapplication.presentationlayer.models.Material.Material
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DiceDBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.TimerDBViewModel


class InstallMaterialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_download_materials)
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val gameId = prefs.getLong("currentGameId", -1)
        val backButton = findViewById<ImageView>(R.id.arrow_back)

        val materialViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MaterialViewModel::class.java]
        val noteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]
        val diceViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DiceDBViewModel::class.java]
        val timerViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[TimerDBViewModel::class.java]


        val recyclerView: RecyclerView = findViewById(R.id.rv_choose_material)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val materialsObserver = Observer<List<Material>> { data ->
            recyclerView.adapter = MaterialRecyclerAdapter(data, noteViewModel, diceViewModel, timerViewModel, true, gameId
            )
        }

        materialViewModel.getAllMaterials().observe(this, materialsObserver)

        backButton.setOnClickListener {
            if (gameId == -1L){
                startActivity(Intent(applicationContext, GameSelectionActivity::class.java))
                prefs.edit().putInt("idmaterial", -1).apply()
                this.finish()
            }
            else {
                startActivity(Intent(applicationContext, ChooseMaterialActivity::class.java))
                prefs.edit().putInt("idmaterial", -1).apply()
                this.finish()
            }

        }
    }
}