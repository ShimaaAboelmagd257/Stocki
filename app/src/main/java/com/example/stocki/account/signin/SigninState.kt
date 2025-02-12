package com.example.stocki.account.signin


sealed class SigninState {
    object Idle : SigninState()
    object Loading : SigninState()
    object Success : SigninState()
    object UserNotFound :SigninState()
    object WrongPassword :SigninState()
    data class Error(val errorMessage: String) : SigninState()
}