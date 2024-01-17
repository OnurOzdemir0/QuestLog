package com.example.questlog.playlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.example.questlog.playlist.GameStatus
import com.example.questlog.playlist.PlayListItem

class PlaylistViewModel : ViewModel() {

    private val _listings = MutableLiveData <MutableList<PlayListItem> >()

    val listings : LiveData <MutableList<PlayListItem>> get() = _listings

    init{
        _listings.value = mutableListOf()
    }
    fun addNewPlaylistItem( item : PlayListItem){
        val currentList = _listings.value ?: mutableListOf()
        currentList.add(item)
        println("added item to playlist")
        _listings.value = currentList
    }
    fun getDataList() : List<PlayListItem>{
        println("playlist items getted " + listings.value?.size)
        return listings.value ?: mutableListOf()
    }

    fun changePlayListItemStatus(item : PlayListItem?,status : GameStatus){
        val currentList = _listings.value ?: mutableListOf()
        if(currentList.contains(item)){
            val index = currentList.indexOf(item)
            currentList[index].gameStatus  = status
            println("playlist item status changed")
        }
        _listings.value = currentList
    }
    fun changePlayListItemUserScore(item : PlayListItem?,score : Int){
        val currentList = _listings.value ?: mutableListOf()
        if(currentList.contains(item)){
            val index= currentList.indexOf(item)
            currentList[index].game.userRating =  score
            println("playlist item user score changed")
        }


        _listings.value = currentList
    }


}