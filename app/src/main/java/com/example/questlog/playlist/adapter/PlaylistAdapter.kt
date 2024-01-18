package com.example.questlog.playlist.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.questlog.R
import com.example.questlog.database.GameDatabase
import com.example.questlog.databinding.ItemPlaylistBinding
import com.example.questlog.playlist.GameStatus
import com.example.questlog.playlist.PlayListItem

class PlaylistCallBacks(val onStatusChange : (item : PlayListItem?, status: GameStatus)->Unit,
                        val onReviewClick : (item : PlayListItem?) -> Unit,
                        val onScoreClick : (item : PlayListItem?, score :Int) -> Unit) {
    fun OnStatusChange(item : PlayListItem?, status : GameStatus) = onStatusChange(item,status)
    fun OnReviewClick(item : PlayListItem?) = onReviewClick(item)
    fun OnScoreClick(item : PlayListItem?, score : Int) = onScoreClick(item,score)

}
class PlaylistAdapter( val callBacks: PlaylistCallBacks) : ListAdapter<PlayListItem, PlaylistAdapter.ItemViewHolder>(
    DiffCallBack
) {

    class ItemViewHolder (val binding : ItemPlaylistBinding)  : RecyclerView.ViewHolder(binding.root){
        val statusMenu  = PopupMenu(binding.root.context,binding.playlistStatusButton)
        
        fun bind(item : PlayListItem, callbacks : PlaylistCallBacks){
            binding.item = item;
            binding.playlistAddReview.setOnClickListener{
                callbacks.onReviewClick(binding.item)
            }
            binding.playlistUserScore.setOnClickListener{
                rateDialogWork(callbacks.onScoreClick)
            }
            binding.playlistStatusButton.setOnClickListener{  statusMenuWork(callbacks.onStatusChange)}

            if( ! item.game.coverImageUrl.isNullOrEmpty() ){
                Glide.with(binding.root.context)
                    .load(item.game.coverImageUrl)
                    .placeholder(R.drawable.i_1)
                    .error(R.drawable.i_1)
                    .into(binding.playlistGameImage)
            }

            binding.executePendingBindings()

        }
        companion object {
            fun from (parent: ViewGroup) : ItemViewHolder {
                val layoutInflater  = LayoutInflater.from(parent.context)
                val binding = ItemPlaylistBinding.inflate(LayoutInflater.from(parent.context))
                return  ItemViewHolder(binding)
            }
        }
        var first : Boolean = true
        private fun statusMenuWork( statusFunc : (item : PlayListItem?, status : GameStatus) -> Unit)  {
            if(first){
                statusMenu.inflate(R.menu.status_change_menu)
                first = false
            }
            
            statusMenu.show()   

            statusMenu.setOnMenuItemClickListener{item :MenuItem ->

                when (item.itemId) {

                    R.id.playing -> {  binding.playlistStatusButton.text = "playing"
                        statusFunc(binding.item, GameStatus.Playing)
                        
                    }
                    R.id.planning_to_play -> {binding.playlistStatusButton.text = "planning to play"
                        statusFunc(binding.item, GameStatus.PlanningToPlay)
                    }
                    R.id.dropped -> {binding.playlistStatusButton.text = "dropped"
                        statusFunc(binding.item, GameStatus.Dropped)
                        
                    }
                    R.id.finished -> {binding.playlistStatusButton.text = "finished"
                        statusFunc(binding.item, GameStatus.Finished)
                        
                    }

                }

                true
            }
            

        }
        private  fun rateDialogWork( scoreChangeFunc : (item : PlayListItem?, score : Int)->Unit){
            val builder = AlertDialog.Builder(binding.root.context)
            val inflater : LayoutInflater =  LayoutInflater.from(binding.root.context);
            val rateDialogLayout = inflater.inflate(R.layout.rate_user_score_layout,null)
            val editText : EditText = rateDialogLayout.findViewById(R.id.user_score_rating_edit_text)

            builder.setTitle("Game Rating")

            builder.setPositiveButton("Rate"){
                    dialog, which -> var rating : Int  = editText.text.toString().toInt()

                if(rating<0)rating  = 0;
                if(rating>100) rating = 100

                binding.playlistUserScore.text = rating.toString()
                binding.userScoreProgressBar.setProgress(rating,false)
                scoreChangeFunc(binding.item,rating)
            }
            builder.setNegativeButton("Cancel"){dialog,which -> }
            builder.setView(rateDialogLayout)

            builder.show()



        }



    }

    companion object  DiffCallBack : DiffUtil.ItemCallback<PlayListItem>(){

        override fun areContentsTheSame(oldItem: PlayListItem, newItem: PlayListItem): Boolean {
            return oldItem.game.gameID == newItem.game.gameID
        }

        override fun areItemsTheSame(oldItem: PlayListItem, newItem: PlayListItem): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,callBacks)
    }


}

