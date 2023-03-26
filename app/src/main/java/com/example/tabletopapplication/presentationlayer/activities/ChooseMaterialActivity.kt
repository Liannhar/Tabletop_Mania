package com.example.tabletopapplication.presentationlayer.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter

class ChooseMaterialActivity : AppCompatActivity() {

    private lateinit var MRadapter: MaterialRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_material)

        MRadapter = MaterialRecyclerAdapter()
        findViewById<RecyclerView>(R.id.rv_choose_material).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MRadapter
        }
    }
}