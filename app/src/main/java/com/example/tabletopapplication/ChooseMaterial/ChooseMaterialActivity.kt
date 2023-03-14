package com.example.tabletopapplication.ChooseMaterial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R

class ChooseMaterialActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.choose_material_activity)

        val recyclerView: RecyclerView = findViewById(R.id.rv_choose_material)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CMRecyclerAdapter(CreateObjects())
    }

    fun CreateObjects():List<Materials>
    {
        return listOf(Materials(),Materials("Кубы","Это кубы",R.drawable.cubes),
        Materials("Заметки","Твои прекрасные заметки",R.drawable.notes))
    }
}