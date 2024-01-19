package com.example.questlog.authentication

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserAuthentication {
    private val db = FirebaseFirestore.getInstance()

    suspend fun login(username: String, password: String): Boolean {
        return try {
            val userDocument = db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .await()
                .documents
                .firstOrNull()

            userDocument?.getString("password") == password
        } catch (e: Exception) {
            false
        }
    }

    suspend fun signUp(username: String, password: String): Boolean {
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
                db.collection("users").add(newUser).await()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
}
