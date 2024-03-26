package com.example.stocki.data.pojos.account

data class UserInfo(
    val uid: Int,
    val name: String,
    val email: String,
    val password: String,
){
    constructor() : this(0, "", "", "")
}