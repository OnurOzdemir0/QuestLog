package com.example.questlog.review

import com.example.questlog.game.GameItem
import com.example.questlog.playlist.PlayListItem

data class ReviewItem(val listing: PlayListItem, var description: String, var isRecommended: Boolean = false)