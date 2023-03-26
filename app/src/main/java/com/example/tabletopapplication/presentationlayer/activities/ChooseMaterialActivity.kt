package com.example.tabletopapplication.presentationlayer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.models.Material
import com.example.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter

class ChooseMaterialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_material)

        val recyclerView: RecyclerView = findViewById(R.id.rv_choose_material)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MaterialRecyclerAdapter(CreateObjects())
    }

    fun CreateObjects(): ArrayList<Material> {
        return arrayListOf(
            Material(), Material("Кубы", "Это кубы", R.drawable.cubes),
            Material("Заметки", "Твои прекрасные заметки", R.drawable.notes)
        )
    }
}