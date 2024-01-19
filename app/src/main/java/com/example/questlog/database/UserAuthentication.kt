package com.example.questlog.database

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class UserAuthentication {
    val auth = FirebaseAuth.getInstance()
    fun signUp(email : String , password: String , onFinish: (Boolean) -> Unit){
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            task -> if(task.isSuccessful) {
                println("Sign up succesful")
                onFinish(true)
        }
        else{
            println(task.exception?.message?: "Sign up Failed")
            onFinish(false)
        }

        }
    }

    fun logIn( email: String, password: String,onFinish: (Boolean) -> Unit ){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onFinish(true)
                } else {
                    println(task.exception?.message ?: "Login failed.")
                    onFinish(false)
                }
            }
    }

    fun logOut(){
        auth.signOut()
    }

    fun getCurrentUser():FirebaseUser?{
        return auth.currentUser
    }
}