package com.example.tabletopapplication.presentationlayer.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.tabletopapplication.R

class DiceSettingsActivity : AppCompatActivity() {

    val dices = arrayOf("d6", "d8", "d12", "d100")
    var selectedDice: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_dice)

        val spinner = findViewById<Spinner>(R.id.dice_setting_spinner)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,dices)
        arrayAdapter.setDropDownViewResource(R.layout.custom_dropdown)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedDice = when (p2) {
                    0 -> 6
                    1 -> 8
                    2 -> 12
                    3 -> 100
                    else -> 6
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        findViewById<Button>(R.id.dice_setting_button).setOnClickListener {
            val intent = Intent(this, DiceResultActivity::class.java).apply {
                putExtra("dice", selectedDice)
            }
            startActivity(intent)
        }

    }
}