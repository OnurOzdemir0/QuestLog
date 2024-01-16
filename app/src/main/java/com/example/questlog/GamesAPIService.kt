package com.example.questlog

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

private const val BASE_URL = "https://api.igdb.com/v4/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GamesAPIService {
    @Headers(
        "Client ID: d3eoune84bn7wpl5y8wfya3wzn0750",
        "Authorization: bearer 8gl2ekedbdue3opfatbx2uzh7gbfso"
    )


    @POST("games/")
    suspend fun listGames(
        @Query("cover,name,summary,rating") fields: String,
        @Query("10") limit: String  // you can change limit from here
    ): List<GamesAPI>

    //Upper method should be sufficient but just in case i will add other requests


    //returns List<GamesAPI> may break code comment out if code ağlarsa
    @POST("covers/")
    suspend fun listCovers(
        @Query("*") fields: String,
        @Query("10") limit: String
    ) : List<GamesAPI>

    @POST("genre/")
    suspend fun listGenres(
        @Query("*") fields: String,
        @Query("10") limit: String
    ) : List<GamesAPI>




    companion object {
        val retrofitService: GamesAPIService by lazy {
            retrofit.create(GamesAPIService::class.java)
        }
    }
}
