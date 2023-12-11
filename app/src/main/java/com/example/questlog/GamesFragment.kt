package com.example.questlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import android.widget.TextView

class GamesFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var gamesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_games, container, false)
        searchView = view.findViewById(R.id.search_view)
        gamesRecyclerView = view.findViewById(R.id.games_recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gamesRecyclerView.layoutManager = LinearLayoutManager(context)
        gamesRecyclerView.adapter = GamesAdapter(getMockGames())
    }

    private fun getMockGames(): List<Game> {
        val context = requireContext()
        val resources = context.resources

        return (1..4).map { gameId ->
            val gameInfo = resources.getStringArray(resources.getIdentifier("game_$gameId", "array", context.packageName))
            Game(
                gameID = gameId,
                name = gameInfo[0],
                desc = gameInfo[1],
                userRating = gameInfo[2].toInt(),
                generalRating = gameInfo[3].toInt()
            )
        }
    }
}

class GamesAdapter(private val gamesList: List<Game>) : RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {

    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.game_image)
        val textView: TextView = view.findViewById(R.id.game_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = gamesList[position]
        holder.textView.text = game.name

        val iconName = "i_${game.gameID}"
        val resourceId = holder.itemView.context.resources.getIdentifier(iconName, "drawable", holder.itemView.context.packageName)
        if (resourceId != 0) { // Resource exists
            holder.imageView.setImageResource(resourceId)
        } else {
            holder.imageView.setImageResource(R.drawable.i_1)
        }
    }

    override fun getItemCount(): Int = gamesList.size
}
