package com.example.questlog.database

import android.content.ContentValues.TAG
import android.util.Log
import com.example.questlog.game.GameItem
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class GameDatabase {
    val db = FirebaseFirestore.getInstance()

    suspend fun getGames(): List<GameItem> {
        return try {
            val gameDocuments = db.collection("games")
                .limit(10)
                .get()
                .await()

            Log.d("firestore", "document ---- ${gameDocuments.documents}")



            gameDocuments.documents.mapNotNull { document ->
                val gameID = document.getLong("id")?.toInt() // Cast to Long then to Int
                val name = document.getString("name") ?: ""
                val desc = document.getString("summary") ?: ""
                Log.d("firestore", "getGames ---------------- $gameID, $name, $desc")
                GameItem(
                    gameID = gameID!!,
                    name = name,
                    desc = desc
                )
            }
        } catch (e: Exception) {
            Log.e("GameDatabase", "Error fetching games", e)
            emptyList()
        }
    }

}

//val db = FirebaseFirestore.getInstance()
