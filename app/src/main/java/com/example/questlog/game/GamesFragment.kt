package com.example.questlog.game

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.questlog.R
import com.example.questlog.database.GameDatabase
import com.example.questlog.databinding.FragmentGamesBinding
import com.example.questlog.game.adapter.GameAdapter
import com.example.questlog.game.adapter.GameCallBacks
import com.example.questlog.playlist.GameStatus
import com.example.questlog.playlist.PlayListItem
import com.example.questlog.playlist.viewmodel.PlaylistViewModel
import com.example.questlog.game.viewmodel.GamesListViewModel

class GamesFragment : Fragment() {

    private lateinit var playListViewModel : PlaylistViewModel
    private lateinit var gamesListViewModel : GamesListViewModel
    private lateinit var binding: FragmentGamesBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_games, container,false
        )
        binding.lifecycleOwner = this

        playListViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        gamesListViewModel = ViewModelProvider(this).get(GamesListViewModel::class.java)


        var adapter : GameAdapter = GameAdapter( GameCallBacks(
            onAddToPlaylist = {item,status -> OnAddToPlaylist(item,status)},
            onStatusChecked = {item -> OnStatusChecked(item)}))
        binding.gamesRecyclerView.adapter  = adapter
        binding.gamesRecyclerView.layoutManager = LinearLayoutManager(context)

        gamesListViewModel.games.observe(viewLifecycleOwner) { gameList ->
            adapter.submitList(gameList)
            Log.d("firestory", gameList.toString())
        }
        return binding.root
    }
    private  fun OnAddToPlaylist(item :GameItem?, status: GameStatus){
        if(item== null) return
        playListViewModel.addNewPlaylistItem(PlayListItem(item,status))
    }
    private  fun OnStatusChecked(game : GameItem? ) : GameStatus?{
        if (game == null) return null
        for (item in playListViewModel.getDataList()) {
            if (item.game.gameID == game.gameID) {

                when (item.gameStatus) {

                    GameStatus.Playing -> {
                        return GameStatus.Playing
                    }

                    GameStatus.PlanningToPlay -> {
                        return GameStatus.PlanningToPlay
                    }

                    GameStatus.Dropped -> {
                        return GameStatus.Dropped
                    }

                    GameStatus.Finished -> {
                        return GameStatus.Finished
                    }

                }

            }
        }
        return  null
    }
//    private fun getMockGames(): List<GameItem> {
//        val context = requireContext()
//        val resources = context.resources
//
//        var list : MutableList<GameItem> = mutableListOf()
//        for(i in 1..15) {
//
//            val gameInfo = resources.getStringArray(resources.getIdentifier("game_$i", "array", context.packageName))
//            val item = GameItem(
//                gameID = i,
//                name = gameInfo[0],
//                desc = gameInfo[1],
//                userRating = gameInfo[2].toInt(),
//                generalRating = gameInfo[3].toInt()
//            )
//            println(item.gameID)
//            list.add(item)
//
//        }
//        return list
//    }

    private val gameDatabase = GameDatabase()
    val games = MutableLiveData<List<GameItem>>()

}
/*
class GamesAdapter(private val gamesList: List<Game>,private val playlistViewModel: PlaylistViewModel) : RecyclerView.Adapter<GamesAdapter.GameViewHolder>() {

    class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.game_image)
        val textView: TextView = view.findViewById(R.id.game_name)
        val addButton : Button = view.findViewById(R.id.game_add_to_playlist)
        private val statusMenu  = PopupMenu(view.context,addButton)
        var first :Boolean = true;
        public  fun showStatusMenu( game : Game, playlistViewModel: PlaylistViewModel) {
            if (first) {
                statusMenu.inflate(R.menu.status_change_menu)
                first = false
            }




                statusMenu.show()


                statusMenu.setOnMenuItemClickListener { item: MenuItem ->

                    when (item.itemId) {

                        R.id.playing -> {
                            addButton.text = "Playing"
                            playlistViewModel.addNewPlaylistItem(PlayListItem(game,GameStatus.Playing))
                        }

                        R.id.planning_to_play -> {
                            addButton.text = "Planning To Play"
                            playlistViewModel.addNewPlaylistItem(PlayListItem(game,GameStatus.PlanningToPlay))
                        }

                        R.id.dropped -> {
                            addButton.text = "Dropped"
                            playlistViewModel.addNewPlaylistItem(PlayListItem(game,GameStatus.Dropped))
                        }

                        R.id.finished -> {
                            addButton.text = "Finished"
                            playlistViewModel.addNewPlaylistItem(PlayListItem(game,GameStatus.Finished))
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
*/