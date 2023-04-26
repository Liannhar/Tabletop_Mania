package com.tabletop.tabletopapplication.presentationlayer.adapters

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tabletop.tabletopapplication.R
import com.tabletop.tabletopapplication.presentationlayer.activities.GamePreviewActivity
import com.tabletop.tabletopapplication.presentationlayer.activities.GameSelectionActivity
import com.tabletop.tabletopapplication.presentationlayer.models.ACTIVITY_REQUEST_CODE
import com.tabletop.tabletopapplication.presentationlayer.models.game.Game
import java.io.InputStream


class GameDbAdapter(
    private var games: ArrayList<Game> = arrayListOf(),
    val contextt:GameSelectionActivity
) : RecyclerView.Adapter<GameDbAdapter.Holder>() {

    class Holder(view: View) : RecyclerView.ViewHolder(view) {

        private val context = view.context
        private val element = view.findViewById<LinearLayout>(R.id.game_element__ll)
        private val name = view.findViewById<TextView>(R.id.game_name)
        private val image = view.findViewById<ImageView>(R.id.game_img)

        fun bind(item: Game,contextt:GameSelectionActivity) {
            name.text = item.name

            Glide.with(contextt)
                .load(item.image)
                .error(R.drawable.baseline_error_outline_24)
                .into(image)
            element.setOnClickListener {
                when(context) {
                    is GameSelectionActivity -> {
                        val intent = Intent(context, GamePreviewActivity::class.java).apply {
                            context.getSharedPreferences("MyPrefsFile", AppCompatActivity.MODE_PRIVATE).edit().putLong("currentGameId",item.id).putInt("gameCount",item.count).apply()

                        }

                        startActivityForResult(context, intent, ACTIVITY_REQUEST_CODE.PREVIEW.value, Bundle())
                        context.finish()
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
        holder.bind(games[position],contextt)

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

    fun updateItems(newItems: List<Game>) {
        // update items and notify adapter of changes
        val diffResult = DiffUtil.calculateDiff(GameDiffCallback(games, newItems))
        games.clear()
        games.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }
}