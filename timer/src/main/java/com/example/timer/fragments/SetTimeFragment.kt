package com.example.timer.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.timer.R

class SetTimeFragment : Fragment(R.layout.set_time_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewId = arguments?.run {
            getInt("viewId")
        } ?: 0

        view.findViewById<Button>(R.id.set_time_button).setOnClickListener {
            val minuets = view.findViewById<EditText>(R.id.minuets).text.run {
                takeIf { isNotEmpty() }?.toString()?.toInt()
            } ?: 0
            val seconds = view.findViewById<EditText>(R.id.seconds).text.run {
                takeIf { isNotEmpty() }?.toString()?.toInt()
            } ?: 0

            parentFragmentManager
                .beginTransaction()
                .replace(viewId, TimerFragment().apply {
                    arguments = Bundle().apply {
                        putInt("minuets", minuets)
                        putInt("seconds",seconds)
                        putInt("viewId", viewId)
                    }
                })
                .commit()

        }
    }
}