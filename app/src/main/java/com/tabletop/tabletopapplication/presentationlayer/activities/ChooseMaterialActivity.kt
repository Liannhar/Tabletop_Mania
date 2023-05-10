package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialRecyclerAdapter
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ChooseMaterialActivity : AppCompatActivity(R.layout.activity_choose_material) {

    private val materialViewModel by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[MaterialViewModel::class.java]
    }

    private val databaseVM by lazy {
        ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DBViewModel::class.java]
    }

    private var currentGameId = -1
    private lateinit var materialsAdapter: MaterialRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentGameId = intent.extras?.run {
            getInt("id", -1)
        } ?: -1

        materialsAdapter = MaterialRecyclerAdapter(DBViewModel = databaseVM, gameId = currentGameId)

        findViewById<RecyclerView?>(R.id.rv_choose_material).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialsAdapter
        }

        val installModules = SplitInstallManagerFactory.create(this).installedModules

        lifecycleScope.launch {
            materialsAdapter.addAll(materialViewModel.getAllMaterials().first())
            installModules.forEach {
                Log.d("DEBUG", it)
            }
        }

        findViewById<ImageView>(R.id.arrow_back).setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }


        findViewById<ImageView>(R.id.download).setOnClickListener {
            startActivity(Intent(this, InstallMaterialActivity::class.java))
        }
    }
}