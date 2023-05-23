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
import com.tabletop.tabletopapplication.presentationlayer.activities.DiceActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.DiceConstants
import com.tabletop.tabletopapplication.presentationlayer.activities.DiceResultActivity


class DiceFragment : Fragment(R.layout.fragment_dice) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var selectedDice = 6
        var countedDice = 1
        val dices = arrayOf("d6", "d8", "d12", "d100")
        val counts = arrayOf("1", "2", "3", "4")

        val spinner1 = view.findViewById<Spinner>(R.id.dice_fragment_spinner1)
        val arrayAdapter1 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, dices)
        arrayAdapter1.setDropDownViewResource(R.layout.custom_dropdown)
        spinner1.adapter = arrayAdapter1
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        val spinner2 = view.findViewById<Spinner>(R.id.dice_fragment_spinner2)
        val arrayAdapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, counts)
        arrayAdapter2.setDropDownViewResource(R.layout.custom_dropdown)
        spinner2.adapter = arrayAdapter2
        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

            // Переход на активити с супер кубанами (пока что обычные)
            val intent = Intent(context, DiceActivity::class.java).apply {
                putExtra(DiceConstants.SERIALIZABLE_DICE_NAME, selectedDice)
            }
            startActivity(intent)
        }
    }


    companion object {
        fun newInstance(): Fragment = DiceFragment()
        const val TAG = "GameListFragment"
    }
}