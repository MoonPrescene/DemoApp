package com.example.demoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.demoapp.model.SignInRes
import com.example.demoapp.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _loginInfo: MutableLiveData<LoginInfo> = MutableLiveData()
    val loginInfo: LiveData<LoginInfo>
        get() = _loginInfo

    val signInRes: LiveData<SignInRes> = _loginInfo.switchMap { loginInfo ->
        userRepository.signIn(loginInfo.username, loginInfo.password)
    }

    fun login(username: String, password: String) {
        val loginInfo = LoginInfo(username, password)
        _loginInfo.value = loginInfo
    }

    data class LoginInfo(val username: String, val password: String)
}