package com.tabletop.tabletopapplication.presentationlayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.businesslayer.models.History

class HistoryAdapter(private val list: List<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_history, null)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(list[position])
    }


    override fun getItemCount(): Int {
        return list.size
    }

    class HistoryViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        protected val date = view.findViewById<TextView>(R.id.date)
        protected val winner = view.findViewById<TextView>(R.id.winner)
        protected val score = view.findViewById<TextView>(R.id.score)

        fun bind(history: History) {
            date.text = "12.04"
            winner.text="Oleg"
            score.text="58 : 44"

        }
    }
}
