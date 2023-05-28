package com.example.timer.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.tabletop.tabletopapplication.R

class SetTimeFragment : Fragment(R.layout.set_time_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val viewId = arguments?.run {
            getInt("viewId")
        } ?: 0

        val button: Button = view.findViewById(R.id.set_time_button)
        button.setOnClickListener {
            val minuets: String = try {
                view.findViewById<EditText>(R.id.minuets).text.toString()
            } catch (error: Throwable) {
                "00"
            }
            val seconds: String = try {
                view.findViewById<EditText>(R.id.seconds).text.toString()
            } catch (error: Throwable) {
                "00"
            }
            parentFragmentManager
                .beginTransaction()
                .replace(viewId, TimerFragment().apply {
                    arguments = Bundle().apply {
                        putString("minuets", minuets)
                        putString("seconds",seconds)
                        putInt("viewId", viewId)
                    }
                })
                .commit()

        }
    }
}