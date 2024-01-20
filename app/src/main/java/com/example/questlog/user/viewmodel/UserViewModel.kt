package com.example.questlog.user.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.questlog.authentication.UserAuthentication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

data class  UserData (var userID: String ,var userName: String?)

class UserViewModel : ViewModel() {
    private val userAuthentication = UserAuthentication()

    private val _currentUser = MutableLiveData<UserData?>()
    val currentUser: MutableLiveData<UserData?> = _currentUser




    fun tryToLogInUser(username: String, password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val loginSuccess = userAuthentication.login(username, password)
            if (loginSuccess !=null) {
                _currentUser.postValue(UserData(loginSuccess, username))
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
            if (signUpSuccess!=null) {
                _currentUser.postValue(UserData(signUpSuccess, username))
            } else {
                _currentUser.postValue(null)
            }
        }
    }

    suspend fun getCurrentUserStats(): List<Int?> {
        val returnVal: MutableList<Int?> = mutableListOf()


        val result = userAuthentication.getUserStats(currentUser.value?.userID ?: "")
        returnVal.add(result?.get("Finished"))
        returnVal.add(result?.get("Playing"))
        returnVal.add(result?.get("Reviewed"))

        // Print or log the values
        println("Finished: ${returnVal[0]}, Playing: ${returnVal[1]}, Reviewed: ${returnVal[2]}")


        return returnVal
    }


}