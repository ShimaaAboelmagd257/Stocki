package com.example.stocki.account.signup


data class SignupIntent(
    val name: String,
    val email: String,
    val password: String,
    val confirmPassword: String
)

