package com.example.questlog.playlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.example.questlog.authentication.UserAuthentication
import com.example.questlog.database.GameDatabase
import com.example.questlog.database.PlaylistDatabase

import com.example.questlog.playlist.GameStatus
import com.example.questlog.playlist.PlayListItem
import kotlinx.coroutines.runBlocking

class PlaylistViewModel() : ViewModel() {

    private val playlistDatabase_ = PlaylistDatabase()
    var userID: String = "kkk"


   suspend fun addNewPlaylistItem( item : PlayListItem){


       if( playlistDatabase_.addPlaylistItem(userID,item.game.gameID,item.gameStatus.toString(),item.userScore))
           println("new item Added")

    }
    fun getDataList() : List<PlayListItem>?{
        val list :List<PlayListItem>?
        runBlocking {
            list  = playlistDatabase_.getPlaylistItems(userID)
        }

        if(list!=null){
            return list
        }else{
            return emptyList()
        }

    }

    suspend fun changePlayListItemStatus(item : PlayListItem?,status : GameStatus){
        if(item !=null){
            if( playlistDatabase_.addPlaylistItem(userID,item.game.gameID,status.toString(),item.userScore))
                println("new item Added")
        }
    }
    suspend fun changePlayListItemUserScore(item : PlayListItem?,score : Int){
        if(item !=null){
            if( playlistDatabase_.addPlaylistItem(userID,item.game.gameID,item.gameStatus.toString(),score))
                println("new item Added")
        }
    }


}