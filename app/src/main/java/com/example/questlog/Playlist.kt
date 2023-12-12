package com.example.questlog

import ReviewViewModel
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.compose.runtime.snapshots.Snapshot.Companion.observe
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.questlog.viewmodel.PlaylistViewModel

import androidx.navigation.fragment.findNavController


class Playlist : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var searchView: SearchView ;
    private lateinit var recyclerView: RecyclerView;
    private lateinit var playlistViewModel: PlaylistViewModel
    private  lateinit var  reviewViewModel: ReviewViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_playlist, container, false)
        searchView = view.findViewById(R.id.playlist_search)
        recyclerView = view.findViewById(R.id.playlist_recyclerview)
        playlistViewModel = ViewModelProvider(requireActivity()).get(PlaylistViewModel::class.java)
        reviewViewModel  = ViewModelProvider(requireActivity()).get(ReviewViewModel::class.java)
        // Inflate the layout for this fragment
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(context)
        playlistViewModel.listings.observe(viewLifecycleOwner) { newList ->

            recyclerView.adapter = PlaylistAdapter(getPlaylist(),playlistViewModel,reviewViewModel)
        }

    }

    private fun getPlaylist(): List<Playlist_Item> {
        return playlistViewModel.getDataList()
    }


}


class PlaylistAdapter(private val playlist_items: List<Playlist_Item>,private val playlistViewModel: PlaylistViewModel,private val reviewViewModel: ReviewViewModel) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {

    class PlaylistViewHolder(private  val view: View) : RecyclerView.ViewHolder(view) {
        val gameImage: ImageView = view.findViewById(R.id.playlist_game_image)
        val gameName: TextView = view.findViewById(R.id.playlist_game_name)
        val generalScore : TextView = view.findViewById(R.id.playlist_game_general_score);
        val userScore : TextView = view.findViewById(R.id.playlist_user_score);
        val statusButton : Button = view.findViewById(R.id.playlist_status_button);
        val statusMenu  = PopupMenu(view.context,statusButton)
        val userScoreBar : ProgressBar = view.findViewById(R.id.user_score_progress_bar)
        val generalScoreBar: ProgressBar = view.findViewById(R.id.general_score_progress_bar)
        val addReviewButton : Button = view.findViewById(R.id.playlist_add_review)
        private var first : Boolean = true

        public  fun showRateDialog(index: Int, playlistViewModel: PlaylistViewModel){

                val builder = AlertDialog.Builder(view.context)
                val inflater : LayoutInflater =  LayoutInflater.from(view.context);
                val rateDialogLayout = inflater.inflate(R.layout.rate_user_score_layout,null)
                val editText : EditText = rateDialogLayout.findViewById(R.id.user_score_rating_edit_text)

                builder.setTitle("Game Rating")

                builder.setPositiveButton("Rate"){
                    dialog, which -> var rating : Int  = editText.text.toString().toInt()

                    if(rating<0)rating  = 0;
                    if(rating>100) rating = 100

                    userScore.text = rating.toString()
                    userScoreBar.setProgress(rating,false)
                    playlistViewModel.changePlayListItemUserScore(index,rating )
                }
                builder.setNegativeButton("Cancel"){dialog,which -> }
                builder.setView(rateDialogLayout)

                builder.show()



        }
        public  fun showStatusMenu( index: Int , playlistViewModel: PlaylistViewModel ) {
            if(first){
                statusMenu.inflate(R.menu.status_change_menu)
                first = false
            }

            statusMenu.show()

            statusMenu.setOnMenuItemClickListener{item :MenuItem ->

                when (item.itemId) {

                    R.id.playing -> {statusButton.text = "Playing"
                                    playlistViewModel.changePlayListItemStatus(index,GameStatus.Playing)
                                   }
                    R.id.planning_to_play -> {statusButton.text = "Planning to play"
                        playlistViewModel.changePlayListItemStatus(index,GameStatus.PlanningToPlay)}
                    R.id.dropped -> {statusButton.text = "Dropped"
                        playlistViewModel.changePlayListItemStatus(index,GameStatus.Dropped)}
                    R.id.finished -> {statusButton.text = "finished"
                        playlistViewModel.changePlayListItemStatus(index,GameStatus.Finished)}

                }

                true
            }

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_playlist, parent, false)
        return PlaylistViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlistListing = playlist_items[position]
        holder.gameName.text =playlistListing.game.name

        val iconName = "i_${playlistListing.game.gameID}"
        val resourceId = holder.itemView.context.resources.getIdentifier(iconName, "drawable", holder.itemView.context.packageName)
        if (resourceId != 0) { // Resource exists
            holder.gameImage.setImageResource(resourceId)
        } else {
            holder.gameImage.setImageResource(R.drawable.i_1)
        }
        holder.generalScore.text = playlistListing.game.generalRating.toString()
        if (playlistListing.game.userRating != null){
            holder.userScore.text  = playlistListing.game.userRating.toString()
        }else{
            holder.userScore.text = "N/A"
        }
        holder.userScoreBar.isIndeterminate  =false

        holder.userScoreBar.progress = (holder.userScore.text.toString().toInt())

        holder.generalScoreBar.setProgress(holder.generalScore.text.toString().toInt(),false)

        when (playlistListing.gameStatus) {

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
            holder.showStatusMenu(position,playlistViewModel);

        }
        holder.userScore.setOnClickListener{
            holder.showRateDialog(position,playlistViewModel)
        }

        holder.addReviewButton.setOnClickListener{
           val navController =  holder.itemView.findNavController()
            var doesExist = false
            for (item in reviewViewModel.getReviewList()) {
                if (item.game.gameID == playlistListing.game.gameID)
                    doesExist = true
            }
            if(!doesExist){
                reviewViewModel.addNewReview(Review(playlistListing.game, "", false))
                navController.navigate(R.id.action_playlist2_to_reviewsFragment)
            }

        }



    }

    override fun getItemCount(): Int = playlist_items.size
}