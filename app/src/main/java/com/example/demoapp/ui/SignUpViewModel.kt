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
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {
    private val _signUpInfo: MutableLiveData<LoginViewModel.LoginInfo> = MutableLiveData()
    val loginInfo: LiveData<LoginViewModel.LoginInfo>
        get() = _signUpInfo

    val signUpRes: LiveData<SignInRes> = _signUpInfo.switchMap {
        userRepository.signIn(it.username, it.password)
    }

    fun login(username: String, password: String) {
        val loginInfo = LoginViewModel.LoginInfo(username, password)
        _signUpInfo.value = loginInfo
    }
}