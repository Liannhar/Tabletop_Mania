package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitcompat.SplitCompat
import com.google.gson.GsonBuilder
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialAdapter
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.APIViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.launch


class InstallMaterialActivity : AppCompatActivity(R.layout.actvity_download_materials) {

    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DBViewModel::class.java]
    }
    private val apiVM = APIViewModel()

    private lateinit var materialAdapter: MaterialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        materialAdapter = MaterialAdapter(this, mode = MaterialAdapter.Mode.INSTALL)

        findViewById<RecyclerView>(R.id.rv_download_material).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialAdapter
        }

        findViewById<ImageView>(R.id.arrow_back).setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }

        lifecycleScope.launch {
            materialAdapter.addAll(apiVM.getRangeMaterials(1, 25))
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        SplitCompat.install(this)
    }
}