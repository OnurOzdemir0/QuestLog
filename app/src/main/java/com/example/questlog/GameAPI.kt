package com.example.questlog

import com.squareup.moshi.Json

data class GamesAPI(
    @Json(name= "results") val results : List<GameAPI>
)
data class GameAPI(val id: String,
                   @Json(name= "cover") val coverPath: String?,
                   val name: String?,
                   val summary: String?,
                   val rating: Double?)

