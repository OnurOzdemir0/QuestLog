package com.example.questlog
import android.media.Image

class Game(
    val gameID: Int,
    val name: String,
    val desc: String,
    var userRating: Int? = null,
    val generalRating: Int? = null,
    val coverImage: Image? = null,
    val reviews: List<Review>? = null
)



