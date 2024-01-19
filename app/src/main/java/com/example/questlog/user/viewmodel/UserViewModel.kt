package com.example.questlog.user.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.questlog.database.UserAuthentication
import com.google.firebase.auth.FirebaseUser

//TODO experimental dunno how to use firebase yet
data class  UserData (var userID: String? ,var userName: String?)
class UserViewModel : ViewModel() {
    val userAuthentication = UserAuthentication()

    private val currentUser_  = MutableLiveData<UserData>()
    val currentUser  : LiveData<UserData> get()  = currentUser_
    fun tryToLogInUser( mail: String,password: String){
        userAuthentication.logIn(mail,password, onFinish = { p ->onLogin(p)})
    }
    fun tryToSignUpUser(mail: String,password: String){
        userAuthentication.signUp(mail,password, onFinish = {})
    }

    fun getCurrentUserFinishedGameCount() : Int {
        // TODO
        return 0
    }
    fun getCurrentUserPlayingGameCount() : Int {
        // TODO
        return 0
    }
    fun getCurrentUserReviewedGameCount() : Int {
        // TODO
        return 0
    }

    private fun onLogin( predicate: Boolean){
        if(predicate){
           val user =  userAuthentication.getCurrentUser()
            currentUser_.value?.userID = user?.uid
            currentUser_.value?.userName = user?.displayName

        }else{
            //TODO handle else
        }
    }
    private fun onSignUp(predicate :Boolean){
        if(predicate){
            //TODO
        }else{
            //TODO
        }
    }
}