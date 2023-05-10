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
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class InstallMaterialActivity : AppCompatActivity(R.layout.actvity_download_materials) {

    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DBViewModel::class.java]
    }

    private val materialViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MaterialViewModel::class.java]
    }

    private lateinit var materialAdapter: MaterialRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        materialAdapter = MaterialRecyclerAdapter(DBViewModel = databaseVM, gameId = -1)

        findViewById<RecyclerView>(R.id.rv_download_material).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialAdapter
        }

        findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }

        fillRV()
    }

    private fun fillRV() {
        lifecycle.coroutineScope.launch {
            materialViewModel.getAllMaterials().collect { data ->
                materialAdapter.addAll(data)
            }
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}