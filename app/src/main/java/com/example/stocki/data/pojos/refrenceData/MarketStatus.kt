package com.example.stocki.data.pojos

import android.os.Parcelable

import kotlinx.parcelize.Parcelize

@Parcelize
data class MarketStatusResponse(
     val afterHours: Boolean,
     val currencies: Currencies,
     val earlyHours: Boolean,
     val exchanges: Exchanges,
     val market: String,
     val serverTime: String
) : Parcelable

@Parcelize
data class Currencies(
    val crypto: String,
     val fx: String
) : Parcelable

@Parcelize
data class Exchanges(
   val nasdaq: String,
     val nyse: String,
     val otc: String
) : Parcelable