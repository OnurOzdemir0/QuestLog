package com.example.questlog.authentication

import android.util.Log
import com.google.android.gms.tasks.NativeOnCompleteListener
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserAuthentication {
    private val db = FirebaseFirestore.getInstance()


    suspend fun login(username: String, password: String): String? {
        return try {
            val userDocument = db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .await()
                .documents
                .firstOrNull()
            userDocument?.getString("password") == password
            return userDocument?.id
        } catch (e: Exception) {
            return null
        }
    }

    suspend fun signUp(username: String, password: String): String? {
        return try {
            val existingUser = db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .await()
                .documents
                .isNotEmpty()

            if (!existingUser) {
                val newUser = hashMapOf(
                    "username" to username,
                    "password" to password
                )
                val doc =  db.collection("users").add(newUser).await()
                return doc.id
            } else {
                return null
            }
        } catch (e: Exception) {
            return  null
        }
    }


    suspend fun getUserStats(userId: String): HashMap<String, Int> {
        val stats = HashMap<String, Int>()

        val playlistItems = db.collection("users")
            .document(userId)
            .collection("playlistItems")

        val queryPlaying = playlistItems.whereEqualTo("status", "Playing").get().await()
        val queryFinished = playlistItems.whereEqualTo("status", "Finished").get().await()
        val playlistItemsSnapshot = db.collection("users")
            .document(userId)
            .collection("playlistItems")
            .get()
            .await()

        var reviewCount = 0

        for (playlistItemDoc in playlistItemsSnapshot.documents) {
            val gameId = playlistItemDoc.id

            val reviewCollection = db.collection("users")
                .document(userId)
                .collection("playlistItems")
                .document(gameId)
                .collection("review")

            val reviewSnapshot = reviewCollection.get().await()

            if (!reviewSnapshot.isEmpty) {

                reviewCount++
            }
        }


        stats["Reviewed"] = reviewCount
        stats["Playing"] = queryPlaying.size()
        stats["Finished"] = queryFinished.size()

        return stats
    }


}
