package com.example.questlog.game.adapter

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.questlog.R
import com.example.questlog.databinding.ItemGameBinding
import com.example.questlog.game.GameItem
import com.example.questlog.playlist.GameStatus

class GameCallBacks( val onAddToPlaylist : (item : GameItem?, status : GameStatus) -> Unit,
                     val onStatusChecked : (item : GameItem?)->GameStatus?){
    fun OnAddToPlaylist(item: GameItem?, status: GameStatus) = onAddToPlaylist(item,status)
    fun OnStatusCheck(item : GameItem?)  : GameStatus?  = onStatusChecked(item)

}

class GameAdapter(val callBacks: GameCallBacks) : ListAdapter<GameItem,GameAdapter.ItemViewHolder>(DiffCallBack) {

    class ItemViewHolder(val binding : ItemGameBinding) : RecyclerView.ViewHolder(binding.root){
        val statusMenu  = PopupMenu(binding.root.context,binding.gameAddToPlaylist)
        fun bind(item : GameItem,callBacks: GameCallBacks){
            binding.game = item
            binding.gameCallbacks = callBacks
            println(item.name)

            // temp image showing
            val iconName = "i_${item.gameID}"
            val resourceId =  binding.root.context.resources.getIdentifier(  iconName, "drawable",binding.root.context.packageName)
            if (resourceId != 0) { // Resource exists
                binding.gameImage.setImageResource(resourceId)
            } else {
                binding.gameImage.setImageResource(R.drawable.i_1)
            }

            var doesExist : Boolean = false

            if(callBacks.onStatusChecked(binding.game) != null){
                doesExist = true
            }
            binding.gameAddToPlaylist.setOnClickListener{ if(!doesExist) statusMenuWork(callBacks.onAddToPlaylist)}
            binding.executePendingBindings()
        }

        companion object {
            fun from (parent: ViewGroup) : GameAdapter.ItemViewHolder {
                val layoutInflater  = LayoutInflater.from(parent.context)
                val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context))
                return GameAdapter.ItemViewHolder(binding)
            }
        }

        var first : Boolean  = true
        private fun statusMenuWork( statusFunc : (item : GameItem?, status : GameStatus) -> Unit)  {
            if(first){
                statusMenu.inflate(R.menu.status_change_menu)
                first = false
            }

            statusMenu.show()

            statusMenu.setOnMenuItemClickListener{item : MenuItem ->

                when (item.itemId) {

                    R.id.playing -> {  binding.gameAddToPlaylist.text = "playing"
                        statusFunc(binding.game, GameStatus.Playing)

                    }
                    R.id.planning_to_play -> {binding.gameAddToPlaylist.text = "planning to play"
                        statusFunc(binding.game, GameStatus.PlanningToPlay)
                    }
                    R.id.dropped -> {binding.gameAddToPlaylist.text = "dropped"
                        statusFunc(binding.game, GameStatus.Dropped)

                    }
                    R.id.finished -> {binding.gameAddToPlaylist.text = "finished"
                        statusFunc(binding.game, GameStatus.Finished)

                    }

                }

                true
            }


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        return ItemViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, callBacks)
    }

    companion object  DiffCallBack : DiffUtil.ItemCallback<GameItem>(){

        override fun areContentsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
            return oldItem.gameID == newItem.gameID
        }

        override fun areItemsTheSame(oldItem: GameItem, newItem: GameItem): Boolean {
            return oldItem == newItem
        }

    }
}