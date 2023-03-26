package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.example.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.NoteViewModel

class ChooseMaterialActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_material)

        val back_button = findViewById<ImageView>(R.id.arrow_back)

        val materialViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MaterialViewModel::class.java]
        val noteViewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[NoteViewModel::class.java]


        val recyclerView: RecyclerView = findViewById(R.id.rv_choose_material)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val materialsObserver = Observer<List<Material>> { data ->
            recyclerView.adapter = MaterialRecyclerAdapter(data,noteViewModel,false,true)
        }
        materialViewModel.getAllMaterials().observe(this,materialsObserver)

        back_button.setOnClickListener{
            startActivity(Intent(applicationContext, EditGameActivity::class.java))
            intent.putExtra("idmaterial",-1)
            this.finish()
        }
    }
}