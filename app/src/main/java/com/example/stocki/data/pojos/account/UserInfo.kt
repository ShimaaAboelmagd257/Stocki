package com.example.stocki.data.pojos.account

data class UserInfo(
    val uid: String,
    val name: String,
    val email: String,
    val balance :Double = 100.00
)