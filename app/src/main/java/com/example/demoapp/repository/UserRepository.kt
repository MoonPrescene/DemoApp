package com.example.demoapp.repository

import androidx.lifecycle.MutableLiveData
import com.example.demoapp.model.SignInRes
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class UserRepository @Inject constructor(

) {
    private val firebaseAuth = FirebaseAuth.getInstance()

    private val loginLiveData = MutableLiveData<SignInRes>()
    private val signUpLiveData = MutableLiveData<SignInRes>()

    fun signIn(email: String, pass: String): MutableLiveData<SignInRes> {
        //this signInWithEmailAndPassword run default on main thread :v
        firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                loginLiveData.postValue(SignInRes.Success)
            } else {
                loginLiveData.postValue(SignInRes.Error(it.exception.toString()))
            }
        }
        return loginLiveData
    }

    fun signUp(email: String, pass: String): MutableLiveData<SignInRes> {
        firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                signUpLiveData.postValue(SignInRes.Success)
            } else {
                signUpLiveData.postValue(SignInRes.Error(it.exception.toString()))
            }
        }
        return signUpLiveData
    }
}