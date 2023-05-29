package com.tabletop.tabletopapplication.presentationlayer.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.adapters.MaterialAdapter
import com.tabletop.tabletopapplication.presentationlayer.contracts.IntentUnitContract
import com.tabletop.tabletopapplication.presentationlayer.viewmodels.DBViewModel
import kotlinx.coroutines.launch


class ChooseMaterialActivity : AppCompatActivity(R.layout.activity_choose_material) {

    private val databaseVM by lazy {
        ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )[DBViewModel::class.java]
    }

    private var currentGameId = -1
    private lateinit var materialsAdapter: MaterialAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentGameId = intent.extras?.run {
            getInt("id", -1)
        } ?: -1

        materialsAdapter = MaterialAdapter(this)

        findViewById<RecyclerView?>(R.id.rv_choose_material).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = materialsAdapter
        }

        findViewById<ImageView>(R.id.arrow_back).setOnClickListener{
            setResult(RESULT_CANCELED)
            finish()
        }

        val installLauncher = registerForActivityResult(IntentUnitContract()) {
            lifecycleScope.launch {
                materialsAdapter.updateAll(databaseVM.getAllMaterials())
            }
        }

        findViewById<ImageView>(R.id.download).setOnClickListener {
            installLauncher.launch(Intent(this, InstallMaterialActivity::class.java))
        }

        lifecycleScope.launch {
            materialsAdapter.addAll(databaseVM.getAllMaterials())
        }
    }
}