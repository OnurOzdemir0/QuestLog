package com.example.questlog.game.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.questlog.database.GameDatabase
import com.example.questlog.game.GameItem



class GamesListViewModel: ViewModel() {
    private val gameDatabase = GameDatabase()

    val games = liveData {
        val gameList = gameDatabase.getGames()
        emit(gameList)
    }
}