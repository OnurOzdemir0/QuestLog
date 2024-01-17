package com.example.questlog

import android.widget.Button
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.questlog.playlist.GameStatus
import com.example.questlog.playlist.PlayListItem

@BindingAdapter("gameStatusSTR")
    fun Button.convertGameStatusToSTR(status: GameStatus?){
        if(status == null){
            text = "add to playlist"
            return
        }
        status.let {
            when(status){
                GameStatus.Finished-> text = "finished"
                GameStatus.Playing-> text = "playing"
                GameStatus.Dropped-> text = "dropped"
                GameStatus.PlanningToPlay -> text= "planning to play"
                else -> text = "status"
            }
        }
    }
