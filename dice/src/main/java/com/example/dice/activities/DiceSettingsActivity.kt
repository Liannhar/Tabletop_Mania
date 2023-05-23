package com.example.dice.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.dice.DiceConstants
import com.example.dice.R

class DiceSettingsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_dice)

        var selectedDice = 6
        val dices = arrayOf("d6", "d8", "d12", "d100")
        val spinner = findViewById<Spinner>(R.id.dice_setting_spinner)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, dices)
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
                putExtra(DiceConstants.SERIALIZABLE_DICE_NAME, selectedDice)
                val gameId = intent.getLongExtra("gameId",-1)
                intent.putExtra("gameId",gameId)
            }
            startActivity(intent)
        }

        findViewById<ImageView>(R.id.dice_setting_goback).setOnClickListener {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }
}