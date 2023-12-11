package com.example.questlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questlog.viewmodel.PlaylistViewModel


class Playlist : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var searchView: SearchView ;
    private lateinit var recyclerView: RecyclerView;
    private lateinit var playlistViewModel: PlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_playlist, container, false)
        searchView = view.findViewById(R.id.playlist_search)
        recyclerView = view.findViewById(R.id.playlist_recyclerview)
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        playlistViewModel.listings.observe(viewLifecycleOwner) { newList ->

            recyclerView.adapter = PlaylistAdapter(getPlaylist())
        }

    }

    private fun getPlaylist(): List<Playlist_Item> {
        return playlistViewModel.getDataList()
    }


}


class PlaylistAdapter(private val playlist_items: List<Playlist_Item>) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    class PlaylistViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val gameImage: ImageView = view.findViewById(R.id.playlist_game_image)
        val gameName: TextView = view.findViewById(R.id.playlist_game_name)
        val generalScore : TextView = view.findViewById(R.id.playlist_game_general_score);
        val userScore : TextView = view.findViewById(R.id.playlist_user_score);
        val statusButton : Button = view.findViewById(R.id.playlist_status_button);
        val statusMenu  = PopupMenu(view.context,statusButton)
        private var first : Boolean = true


        public  fun showStatusMenu( prevStatus: GameStatus ) : GameStatus{
            if(first){
                statusMenu.inflate(R.menu.status_change_menu)
                first = false
            }

            statusMenu.show()
            var currentStatus : GameStatus = prevStatus;
            statusMenu.setOnMenuItemClickListener{item :MenuItem ->

                when (item.itemId) {

                    R.id.playing -> {statusButton.text = "Playing"
                                    currentStatus = GameStatus.Playing}
                    R.id.planning_to_play -> {statusButton.text = "Planning to play"
                        currentStatus = GameStatus.PlanningToPlay}
                    R.id.dropped -> {statusButton.text = "Dropped"
                    currentStatus= GameStatus.Dropped}
                    R.id.finished -> {statusButton.text = "finished"
                    currentStatus= GameStatus.Finished}

                }

                true
            }
            return currentStatus
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist_listing = playlist_items[position]
        holder.gameName.text =playlist_listing.game.name

        val iconName = "i_${playlist_listing.game.gameID}"
        val resourceId = holder.itemView.context.resources.getIdentifier(iconName, "drawable", holder.itemView.context.packageName)
        if (resourceId != 0) { // Resource exists
            holder.gameImage.setImageResource(resourceId)
        } else {
            holder.gameImage.setImageResource(R.drawable.i_1)
        }
        holder.generalScore.text = 80.toString()
        holder.userScore.text  = 80.toString()

        when (playlist_listing.gameStatus) {

            GameStatus.Playing -> {holder.statusButton.text = "Playing"
                }
            GameStatus.PlanningToPlay -> {holder.statusButton.text = "Planning to play"
                 }
            GameStatus.Dropped-> {holder.statusButton.text = "Dropped"
                 }
            GameStatus.Finished -> {holder.statusButton.text = "finished"
                 }

        }
        holder.statusButton.setOnClickListener{
            val newStatus = holder.showStatusMenu(playlist_listing.gameStatus);
            playlist_listing.gameStatus = newStatus;
            println(playlist_listing.gameStatus)
        }



    }

    override fun getItemCount(): Int = playlist_items.size
}