package com.example.tabletopapplication.ChooseMaterial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.common.Material
import com.example.tabletopapplication.common.adapters.CMRecyclerAdapter

class ChooseMaterialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_material_activity)

        val recyclerView: RecyclerView = findViewById(R.id.rv_choose_material)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CMRecyclerAdapter(CreateObjects())
    }

    fun CreateObjects(): ArrayList<Material> {
        return arrayListOf(
            Material(), Material("Кубы", "Это кубы", R.drawable.cubes),
            Material("Заметки", "Твои прекрасные заметки", R.drawable.notes)
        )
    }
}