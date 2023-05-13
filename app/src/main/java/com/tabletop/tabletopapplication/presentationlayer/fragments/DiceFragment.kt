package com.tabletop.tabletopapplication.presentationlayer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.tabletop.tabletopapplication.R


class DiceFragment : Fragment(R.layout.fragment_dice) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        var selectedDice = 6
        val dices = arrayOf("d6", "d8", "d12", "d100")
        val spinner = view.findViewById<Spinner>(R.id.dice_fragment_spinner)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, dices)
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

        view.findViewById<Button>(R.id.dice_fragment_button).setOnClickListener {
            // Переход на новый экран

        }


    }


    companion object {
        fun newInstance(): Fragment = DiceFragment()
        const val TAG = "GameListFragment"
    }
}