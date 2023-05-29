package com.tabletop.tabletopapplication.presentationlayer.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.tabletop.tabletopapplication.R

class HistoryFragment : Fragment(R.layout.history_add_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val button: Button = view.findViewById(R.id.save_history_button)
        button.setOnClickListener {

            //строчка с датой

            val date: String = try {
                view.findViewById<EditText>(R.id.date).text.toString()
            } catch (error: Throwable) {
                "-"

            }

            //строчка с победителем

            val winner: String = try {
                view.findViewById<EditText>(R.id.winner).text.toString()
            } catch (error: Throwable) {
                "-"
            }

             //строчка с очками

            val score: String = try {
                view.findViewById<EditText>(R.id.score).text.toString()
            } catch (error: Throwable) {
                "-"
            }

            parentFragmentManager
                .beginTransaction()
                .remove(this)
                .commit()

        }
    }

    companion object {

        fun newInstance(): Fragment = HistoryFragment()

        //  const val TAG = "GameListFragment"
    }
}