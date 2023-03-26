package com.example.tabletopapplication.presentationlayer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.tabletopapplication.BuildConfig
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.managers.MaterialManager
import com.example.tabletopapplication.businesslayer.models.MaterialEntity
import com.example.tabletopapplication.businesslayer.models.Response
import com.example.tabletopapplication.presentationlayer.fragments.GameListFragment
import com.example.tabletopapplication.presentationlayer.fragments.NavBarFragment
import com.example.tabletopapplication.presentationlayer.models.Material.Material
import com.example.tabletopapplication.presentationlayer.viewmodels.MaterialViewModel
import com.example.tabletopapplication.presentationlayer.viewmodels.NoteViewModel
import retrofit2.http.GET
import retrofit2.http.Path

class GameSelectionActivity : AppCompatActivity() {

    val materialViewModel by lazy{ ViewModelProvider(
        this,
        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
    )[MaterialViewModel::class.java]}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.game_selection, null)
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, NavBarFragment.newInstance(), NavBarFragment.TAG)
                .replace(R.id.recycler_host, GameListFragment.newInstance(), GameListFragment.TAG)
                .commit()
        }
        Log.i("iSAGIII","check")
        DBCheck()
    }

    private fun DBCheck(){

        val currentVersionCode = BuildConfig.VERSION_CODE
        val prefs = getSharedPreferences("MyPrefsFile", MODE_PRIVATE)
        val savedVersionCode = prefs.getInt("version_code", -1)
        Log.i("AAA",savedVersionCode.toString())
        if (savedVersionCode == -1) {
            // This is a new install (or the user cleared the shared preferences)
            fillRoom()
        } else if (currentVersionCode > savedVersionCode) {
            // This is an upgrade
        }

        prefs.edit().putInt("version_code", currentVersionCode).apply()
    }
    private fun fillRoom() {
        materialViewModel.getAllMaterialsFromApi()
    }
}