package com.example.tabletopapplication.Dice

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.tabletopapplication.R
import com.example.tabletopapplication.presentationlayer.activities.DiceSettingsActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DiceResultActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dice_result)

        findViewById<ImageView>(R.id.dice_result_goback).setOnClickListener {
            val intent = Intent(this, DiceSettingsActivity::class.java)
            startActivity(intent)
        }

        lifecycleScope.launch {
            showAnimation()
        }
    }

    @SuppressLint("SetTextI18n")
    private suspend fun showAnimation() {
        val img = findViewById<ImageView>(R.id.dice_result_pic)
        Glide.with(this).load(R.drawable.kuban_stub).into(img)
        val res = findViewById<TextView>(R.id.dice_result_cv_3_text)
        res.text = "Смотрим анимацию."
        delay(1000)
        res.text = "Смотрим анимацию.."
        delay(1000)
        res.text = "Смотрим анимацию..."
        delay(1000)
        Glide.with(this).load(R.drawable.double_dice).into(img)
        val item = intent.getIntExtra(DiceConstants.SERIALIZABLE_DICE_NAME, DiceConstants.DEFAULT_FACE_OF_THE_CUBE)
        if (item == 12) {
            res.text = "Выпало в сумме: ${(2..item).random()}"
        }
        else {
            res.text = "Выпало в сумме: ${(1..item).random()}"
        }
    }
}