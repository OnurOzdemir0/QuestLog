package com.example.questlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.questlog.viewmodel.PlaylistViewModel

class GamesFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var gamesRecyclerView: RecyclerView
    private lateinit var playListViewModel : PlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_games, container, false)
        searchView = view.findViewById(R.id.search_view)
        gamesRecyclerView = view.findViewById(R.id.games_recycler_view)
        playListViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gamesRecyclerView.layoutManager = LinearLayoutManager(context)
        gamesRecyclerView.adapter = GamesAdapter(getMockGames(),playListViewModel)
    }

    private fun getMockGames(): List<Game> {
        val context = requireContext()
        val resources = context.resources

        return (1..15).map { gameId ->
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

class GamesAdapter(private val gamesList: List<Game>,private val playlistViewModel: PlaylistViewModel) : RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {


    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.game_image)
        val textView: TextView = view.findViewById(R.id.game_name)
        val addButton : Button = view.findViewById(R.id.game_add_to_playlist)
        private val statusMenu  = PopupMenu(view.context,addButton)
        var first :Boolean = true;
        public  fun showStatusMenu( game : Game, playlistViewModel: PlaylistViewModel   ) {
            if (first) {
                statusMenu.inflate(R.menu.status_change_menu)
                first = false
            }




                statusMenu.show()


                statusMenu.setOnMenuItemClickListener { item: MenuItem ->

                    when (item.itemId) {

                        R.id.playing -> {
                            addButton.text = "Playing"
                            playlistViewModel.addNewPlaylistItem(Playlist_Item(game,GameStatus.Playing))
                        }

                        R.id.planning_to_play -> {
                            addButton.text = "Planning To Play"
                            playlistViewModel.addNewPlaylistItem(Playlist_Item(game,GameStatus.PlanningToPlay))
                        }

                        R.id.dropped -> {
                            addButton.text = "Dropped"
                            playlistViewModel.addNewPlaylistItem(Playlist_Item(game,GameStatus.Dropped))
                        }

                        R.id.finished -> {
                            addButton.text = "Finished"
                            playlistViewModel.addNewPlaylistItem(Playlist_Item(game,GameStatus.Finished))
                        }

                    }

                    true
                }


        }
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

        var doesExist = false
        for (item in playlistViewModel.getDataList()) {
            if (item.game.gameID == game.gameID){
                doesExist = true
                when (item.gameStatus) {

                    GameStatus.Playing -> {holder.addButton.text = "Playing"
                    }
                    GameStatus.PlanningToPlay -> {holder.addButton.text = "Planning to play"
                    }
                    GameStatus.Dropped-> {holder.addButton.text = "Dropped"
                    }
                    GameStatus.Finished -> {holder.addButton.text = "finished"
                    }

                }

            }

        }

        holder.addButton.setOnClickListener{
            if (!doesExist) {
                holder.showStatusMenu(game, playlistViewModel)
            }
        }
    }

    override fun getItemCount(): Int = gamesList.size
}
