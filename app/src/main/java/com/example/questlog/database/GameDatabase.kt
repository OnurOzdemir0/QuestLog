package com.example.questlog.database

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.questlog.R
import com.example.questlog.game.GameItem
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Math.floor


//we tried doing API only first, but the API was very hard to work with in Kotlin. So we decided to store the same data of API in Firestore.
//Now we use Firestore for the data, but we still use the API for the images. Image retrieval is done directly from URL via Glide.

class GameDatabase {
    val db = FirebaseFirestore.getInstance()

    suspend fun getGames(): List<GameItem> {
        return try {
            val gameDocuments = db.collection("games")
                .limit(3)
                .get()
                .await()

            Log.d("firestore", "document ---- ${gameDocuments.documents}")

            gameDocuments.documents.mapNotNull { document ->
                val gameID = document.getLong("id")?.toInt() // Cast to Long then to Int
                val name = document.getString("name") ?: ""
                val desc = document.getString("summary") ?: ""
                val rating = floor(document.getDouble("rating") ?: 0.0).toInt()

                //Images are retrieved by their image_id, which is a hashed string.
                //Base URL is https://images.igdb.com/igdb/image/upload/t_{size}/{image_id}.jpg
                // for covers I use t_cover_big
                // for screenshots I use t_1080p,

                // covers are portrait format so they are used in the playlist and Games lists
                // screenshots are landscape so they are used in reviews.

                // Similar logic is found in Letterboxd, where the cover is portrait and the screenshots/fan artworks are landscape.
                // so detailed page has screenshot as the primary image

                //COVER
                val coverMap = document.get("cover") as? Map<*, *>
                val coverImageId = coverMap?.get("image_id") as? String
                val coverImageUrl = coverImageId?.let { "https://images.igdb.com/igdb/image/upload/t_cover_big/$it.jpg" }

                //SCREENSHOT - games can have multiple screenshots, and these lists are asymetric. Some have 2, some have 10.
                val screenshotsList = document.get("screenshots") as? List<Map<*, *>>
                val screenshotUrls = screenshotsList?.mapNotNull {
                    it["image_id"] as? String
                }?.map {
                    "https://images.igdb.com/igdb/image/upload/t_1080p/$it.jpg"
                }

                Log.d("firestore", "getGames ---------------- $gameID, $name, $desc")
                GameItem(
                    gameID = gameID!!,
                    name = name,
                    desc = desc,
                    generalRating = rating,
                    coverImageUrl = coverImageUrl,
                    screenshotUrl = screenshotUrls
                )
            }
        } catch (e: Exception) {
            Log.e("GameDatabase", "Error fetching games", e)
            emptyList()
        }
    }
}

//val db = FirebaseFirestore.getInstance()
