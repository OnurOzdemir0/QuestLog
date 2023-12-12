package com.example.questlog.viewmodel

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.questlog.Game
import com.example.questlog.GameStatus
import com.example.questlog.Playlist_Item

class PlaylistViewModel : ViewModel() {

    private val _listings = MutableLiveData <MutableList<Playlist_Item> >()

    val listings : LiveData <MutableList<Playlist_Item>> get() = _listings

    init{
        _listings.value = mutableListOf()
    }
    fun addNewPlaylistItem( item : Playlist_Item){
        val currentList = _listings.value ?: mutableListOf()
        currentList.add(item)
        _listings.value = currentList
    }
    fun getDataList() : List<Playlist_Item>{
        return listings.value ?: mutableListOf()
    }

    fun changePlayListItemStatus(index : Int,status : GameStatus){
        val currentList = _listings.value ?: mutableListOf()
        currentList[index].gameStatus =  status
        _listings.value = currentList
    }


}