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




//    private val _response = MutableLiveData<String>()
//    val response: MutableLiveData<String>
//        get() = _response
//
//    private val _games = MutableLiveData<GamesAPI>()
//    val games: MutableLiveData<GamesAPI>
//        get() = _games
//
//    private fun getGames() {
//        viewModelScope.launch {
//            try {
//                val listResult = GamesAPIService.retrofitService.listGames("cover,name,summary,rating","10")
//                _games.value = listResult
//                _response.value = "Success : ${listResult.results.size} Movies retrieved"
//                Log.v("View Model", "${_response.value}")
//            } catch (e: Exception) {
//                _response.value = "Failure " + e.message
//                Log.v("View Model", "${_response.value}")
//            }
//        }
//    }
//    init {
//        getGames()
//    }
//}

//class GamesListViewModelFactory(private val application: Application) : androidx.lifecycle.ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(GamesListViewModel::class.java)) {
//            return GamesListViewModel(application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}