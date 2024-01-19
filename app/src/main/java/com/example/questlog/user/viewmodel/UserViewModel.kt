package com.example.questlog.user.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.questlog.authentication.UserAuthentication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

data class  UserData (var userID: String? ,var userName: String?)

class UserViewModel : ViewModel() {
    private val userAuthentication = UserAuthentication()

    private val _currentUser = MutableLiveData<UserData?>()
    val currentUser: MutableLiveData<UserData?> = _currentUser

    fun tryToLogInUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val loginSuccess = userAuthentication.login(username, password)
            if (loginSuccess) {
                _currentUser.postValue(UserData(null, username))
                Log.d("UserViewModel", "Login success")
            } else {
                _currentUser.postValue(null)
                Log.d("UserViewModel", "Login failed")
            }
        }
    }

    fun tryToSignUpUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val signUpSuccess = userAuthentication.signUp(username, password)
            if (signUpSuccess) {
                _currentUser.postValue(UserData(null, username))
            } else {
                _currentUser.postValue(null)
            }
        }
    }

    fun getCurrentUserFinishedGameCount(): Int {
        return 0
    }

    fun getCurrentUserReviewedGameCount(): Int {
        return 0
    }

    fun getCurrentUserPlayingGameCount(): Int {
        return 0
    }
}