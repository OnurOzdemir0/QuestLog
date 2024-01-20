package com.example.questlog.database

import android.util.Log
import com.example.questlog.game.GameItem
import com.example.questlog.playlist.GameStatus
import com.example.questlog.playlist.PlayListItem
import com.example.questlog.user.viewmodel.UserData
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import java.util.UUID

class PlaylistDatabase {

    private val db = FirebaseFirestore.getInstance()


    suspend fun  addPlaylistItem(userId: String, gameId : String, status : String, userScore : Int = 0) : Boolean{
        val playlistItem = HashMap<String,Any>()
        playlistItem["status"] = status
        playlistItem["userScore"] = userScore
        return  try {
            db.collection("users")
                .document(userId)
                .collection("playlistItems")
                .document(gameId)
                .set( playlistItem , SetOptions.merge())
                .await()
            return  true

        }catch (e :Exception){
            Log.e( "Playlist", "Error trying to create playlist",e)
            return false
        }

    }

    suspend fun getPlaylistItems(userId: String) : List<PlayListItem>{

        val gameItems  : MutableList<GameItem> = mutableListOf()


        // get the docs
        val playlist_docs = db.collection("users")
            .document(userId)
            .collection("playlistItems")
            .get()
            .await()
        // get the
        val status  : MutableList<String>  = mutableListOf()
        val userScores : MutableList<Int> = mutableListOf()
        val games = playlist_docs.documents.mapNotNull{documentSnapshot ->
                val gameId  = documentSnapshot.id
                val status_ = documentSnapshot.getString("status").toString() ?: ""
                val int : Int = 0
                val userScore_ = (documentSnapshot.getDouble("userScore")?:0.0).toInt()
                userScores.add(userScore_)
                status.add(status_)
                gameId
        }

        for( g in games) {
            val snp = db.collection("games")
                .whereEqualTo("id", g.toInt())
                .get()
                .await()
                .documents
                .firstOrNull()
            val gameID = snp?.getLong("id")?.toInt().toString()
            val name = snp?.getString("name") ?: ""
            val desc = snp?.getString("summary") ?: ""
            val rating = Math.floor(snp?.getDouble("rating") ?: 0.0).toInt()
            val coverMap = snp?.get("cover") as? Map<*, *>
            val coverImageId = coverMap?.get("image_id") as? String
            val coverImageUrl = coverImageId?.let { "https://images.igdb.com/igdb/image/upload/t_cover_big/$it.jpg" }


            val screenshotsList = snp?.get("screenshots") as? List<Map<*, *>>
            val screenshotUrls = screenshotsList?.mapNotNull {
                it["image_id"] as? String
            }?.map {
                "https://images.igdb.com/igdb/image/upload/t_1080p/$it.jpg"
            }

           gameItems.add(GameItem(
                gameID = gameID,
                name = name,
                desc = desc,
                generalRating = rating,
                coverImageUrl = coverImageUrl,
                screenshotUrl = screenshotUrls
            ))
        }
        val items : MutableList<PlayListItem> = mutableListOf()
        for ( k in 0 until gameItems.size){
            val st = status.get(k)

            items.add(PlayListItem(
                gameItems.get(k),
                when(st){
                    GameStatus.PlanningToPlay.toString() -> GameStatus.PlanningToPlay
                    GameStatus.Playing.toString()-> GameStatus.Playing
                    GameStatus.Dropped.toString() -> GameStatus.Dropped
                    else -> GameStatus.Finished

                },
                userScores.get(k)
            ))

        }


        return  items
    }



}