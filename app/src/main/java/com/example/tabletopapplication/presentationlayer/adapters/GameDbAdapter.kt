package com.example.tabletopapplication.presentationlayer.adapters

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tabletopapplication.R
import com.example.tabletopapplication.businesslayer.models.GameEntity
import com.example.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.example.tabletopapplication.presentationlayer.activities.GameSelectionActivity
import com.example.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.example.tabletopapplication.presentationlayer.models.game.Game

class GameDbAdapter(
    private var games: ArrayList<Game> = arrayListOf()
) : RecyclerView.Adapter<GameDbAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        private val context = view.context
        private val element = view.findViewById<LinearLayout>(R.id.game_element__ll)
        private val name = view.findViewById<TextView>(R.id.game_name)
        private val image = view.findViewById<ImageView>(R.id.game_img)

        fun bind(item: Game) {
            name.text = item.name

            Glide.with(image)
                .load(item.image)
                .error(R.drawable.baseline_error_outline_24)
                .into(image)

            element.setOnClickListener {
                when(context) {
                    is GameSelectionActivity -> {
                        val intent = Intent(context, GamePreviewActivity::class.java).apply {
                            putExtra("gameId", item.id) }
                        startActivityForResult(context, intent, ACTIVITY_REQUEST_CODE.PREVIEW.value, Bundle())
                    }
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_element, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(games[position])

    override fun getItemCount(): Int = games.size

    fun submitItems(data: ArrayList<Game>) {
        games = data
        notifyItemRangeChanged(0, games.size - 1)
    }

    fun addGame(game: Game) {
        games.add(game)
        notifyItemInserted(games.size - 1)
    }

    fun addListGames(games: List<Game>) {
        this.games.addAll(games)
        notifyItemRangeInserted(this.games.size - games.size, games.size)
    }

    fun getGamePosition(game: Game): Int {
        for (i in 0 until games.size)
            if (game.id == games[i].id)
                return i

        return -1
    }

    fun changeGame(game: Game) {
        val position = getGamePosition(game)
        if (position == -1)
            return

        games[position] = game
        notifyItemChanged(position)
    }
}