package com.example.stocki.data.pojos.account

data class PortfolioItem (
    val ticker :String,
    val quantity :Int,
    val  averagePrice :Double
)
data class userTransaction(
    val type: String, // "buy" or "sell"
    val ticker: String,
    val quantity: Int,
    val price: Double,
    val timestamp: Long
)