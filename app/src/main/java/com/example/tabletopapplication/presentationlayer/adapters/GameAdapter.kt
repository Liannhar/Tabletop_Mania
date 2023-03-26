package com.example.tabletopapplication.presentationlayer.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity

@SuppressLint("NotifyDataSetChanged")
class GameAdapter(
    private var games: ArrayList<GameEntity> = arrayListOf()
) : RecyclerView.Adapter<GameAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val name = view.findViewById<TextView>(R.id.game_name)
        val image = view.findViewById<ImageView>(R.id.game_img)

        fun bind(item: GameEntity) {
            name.text = item.name

            Glide.with(image)
                 .load(item.image)
                 .error(R.drawable.black_rectangle)
                 .into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_element, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(games[position])

    override fun getItemCount(): Int = games.size

    fun submitItems(data: ArrayList<GameEntity>) {
        games = data
        notifyDataSetChanged()
    }

    fun addGame(game: GameEntity) {
        games.add(game)
        notifyItemChanged(games.size - 1)
    }

    fun addListGames(games: List<GameEntity>) {
        this.games.addAll(games)
        notifyItemRangeInserted(this.games.size - games.size, games.size)
    }
}