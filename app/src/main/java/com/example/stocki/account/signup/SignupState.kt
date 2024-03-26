package com.example.stocki.account.signup

import com.example.stocki.account.signin.SigninState

sealed class SignupState {

    object Idle : SignupState()
    object Loading : SignupState()
    object Success : SignupState()
    data class Error(val error:String) : SignupState()
}