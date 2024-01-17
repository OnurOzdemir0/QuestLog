package com.example.questlog.playlist


import com.example.questlog.game.GameItem

public enum class GameStatus{
    PlanningToPlay,
    Playing,
    Finished,
    Dropped
}
data class PlayListItem(

    val game : GameItem,
    var gameStatus: GameStatus
)