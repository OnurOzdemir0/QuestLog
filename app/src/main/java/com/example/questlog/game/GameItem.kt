package com.example.questlog.game

import android.media.Image
import com.example.questlog.Review

data class GameItem(
    val gameID: Int,
    val name: String,
    val desc: String,
    var userRating: Int? = null,
    val generalRating: Int? = null,
    val coverImage: Image? = null,
    val reviews: List<Review>? = null,
    val coverImageUrl: String? = null,
    val screenshotUrl: List<String>? = null
)