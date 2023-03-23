package com.example.tabletopapplication.Dice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.tabletopapplication.R

class DiceResultActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_result)

        findViewById<ImageView>(R.id.dice_result_goback).setOnClickListener {
            val intent = Intent(this, DiceSettingsActivity::class.java)
            startActivity(intent)
        }

        val item = intent.getSerializableExtra("dice")
        val res = findViewById<TextView>(R.id.dice_result_cv_3_text)
        res.setText("Выпало в сумме: ${item.toString()}")
    }
}