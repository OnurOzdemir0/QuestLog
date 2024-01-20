package com.example.questlog.authentication

import android.widget.Toast
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
    suspend fun changePassword(userName: String, oldPassword: String, newPassword:String) : Boolean{
        return try {
            val userQuery = db.collection("users")
                .whereEqualTo("username", userName)
                .get()
                .await()

            if (!userQuery.isEmpty) {
                val userDocument = userQuery.documents[0]
                val userId = userDocument.id
                val storedPassword = userDocument.getString("password")

                // Check if the provided old password matches the stored password
                if (storedPassword == oldPassword) {
                    // Update the password field in Firestore with the new password
                    db.collection("users").document(userId)
                        .update("password", newPassword)
                        .await()

                    true
                } else {
                    false  // Old password doesn't match
                }
            } else {
                false  // User not found
            }
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
