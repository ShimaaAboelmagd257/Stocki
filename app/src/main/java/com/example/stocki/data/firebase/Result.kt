package com.example.stocki.data.firebase

sealed class tradingResult {
    object Success : tradingResult()
    data class Error(val message: String) : tradingResult()
}