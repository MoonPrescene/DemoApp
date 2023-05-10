package com.example.demoapp.model

import java.lang.Exception

sealed class SignInRes {
    object Success : SignInRes()
    data class Error(val message: String) : SignInRes()
}
