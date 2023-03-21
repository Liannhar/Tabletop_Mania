package com.example.tabletopapplication.Dice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.tabletopapplication.R

class DiceSettingsActivity : AppCompatActivity() {

    val dices = arrayOf("d6", "d8", "d12", "d100")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_dice)

        val spinner = findViewById<Spinner>(R.id.dice_setting_spinner)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,dices)
        arrayAdapter.setDropDownViewResource(R.layout.custom_dropdown)
        spinner.adapter = arrayAdapter

    }
}