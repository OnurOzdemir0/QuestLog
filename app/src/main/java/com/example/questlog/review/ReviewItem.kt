package com.example.questlog.review

import com.example.questlog.game.GameItem

data class ReviewItem(val game: GameItem, var description: String, var isRecommended: Boolean = false)