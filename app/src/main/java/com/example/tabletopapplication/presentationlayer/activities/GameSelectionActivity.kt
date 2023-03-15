package com.example.tabletopapplication.presentationlayer.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.fragments.GameListFragment
import com.example.tabletopapplication.presentationlayer.fragments.NavBarFragment

class GameSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val view = layoutInflater.inflate(R.layout.game_selection, null)
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragmentContainerView, NavBarFragment.newInstance(), NavBarFragment.TAG)
                .replace(R.id.recycler_host, GameListFragment.newInstance(), GameListFragment.TAG)
                .commitNow()
        }
    }
}