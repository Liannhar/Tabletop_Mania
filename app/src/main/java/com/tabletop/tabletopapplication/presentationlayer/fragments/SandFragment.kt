package com.tabletop.tabletopapplication.presentationlayer.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.DiceConstants
import com.tabletop.tabletopapplication.presentationlayer.activities.DiceResultActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.HourglassActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.SandConstants


class SandFragment : Fragment(R.layout.fragment_sand) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        var selectedTime = 5000
        val times = arrayOf("5", "10", "15", "20")

        val spinner = view.findViewById<Spinner>(R.id.sand_fragment_spinner1)
        val arrayAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, times)
        arrayAdapter.setDropDownViewResource(R.layout.custom_dropdown)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                selectedTime = when (p2) {
                    0 -> 5000
                    1 -> 10000
                    2 -> 15000
                    3 -> 20000
                    else -> 5000
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }



        view.findViewById<Button>(R.id.sand_fragment_button).setOnClickListener {

            val intent = Intent(context, HourglassActivity::class.java).apply {
                putExtra(SandConstants.SERIALIZABLE_SAND_NAME, selectedTime)
            }
            startActivity(intent)
        }


    }


    companion object {
        fun newInstance(): Fragment = SandFragment()
        const val TAG = "GameListFragment"
    }


}