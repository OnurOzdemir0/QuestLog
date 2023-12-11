package com.example.questlog

public enum class GameStatus{
    PlanningToPlay,
    Playing,
    Finished,
    Dropped
}
class Playlist_Item (
    val game : Game,
    var gameStatus: GameStatus
)