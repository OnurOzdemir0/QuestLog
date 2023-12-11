package com.example.questlog.viewmodel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserViewModel :ViewModel(){
    private val _userName = MutableLiveData<String>()
    val userName : LiveData<String> get() =  _userName

    private val _mail  = MutableLiveData<String>()

    val mail : LiveData<String> get() =_mail


    fun setUserName( name : String){
        _userName.value = name
    }
    fun setUserMail(mail :String){
        _mail.value = mail
    }


}