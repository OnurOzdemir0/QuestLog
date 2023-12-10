package com.example.questlog
import android.media.Image

class Game(
    val gameID: Int,
    val name: String,
    val desc: String,
    val userRating: Int? = null,
    val generalRating: Int? = null,
    val coverImage: Image? = null,
    val reviews: List<Review>? = null
)

class Review(
    val reviewID: Int,
    val gameID: Int,
    val userID: Int,
    val rating: Int? = null,
    val reviewText: String
)