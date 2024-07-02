package com.example.stocki.data.pojos.account

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PortfolioItem (
    val ticker :String = "",
    val quantity :Int = 1,
    val averagePrice :Double = 0.0
) : Parcelable

@Parcelize
data class userTransaction(
    val type: String, // "buy" or "sell"
    val ticker: String,
    val quantity: Int,
    val price: Double,
    val timestamp: Long
) : Parcelable