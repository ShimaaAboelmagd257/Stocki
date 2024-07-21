package com.example.stocki.data.pojos

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class TickerResponse(
    val count: Int,
    val nextUrl: String,
    val requestId: String,
    val results: List<Ticker>,
    val status: String
): Parcelable


@Parcelize
data class Ticker(
    val active: Boolean,
    val cik: String,
    val compositeFigi: String,
    val currencyName: String,
     val lastUpdatedUtc: String,
    val locale: String,
    val market: String,
    val name: String,
    val primaryExchange: String,
     val shareClassFigi: String,
    val ticker: String,
    val type: String
): Parcelable

//@Entity(tableName = "Tickertypes")
data class TickerTypes(
  //  @PrimaryKey(autoGenerate = true)
    val id:String = "" ,
    val market: String = "",
    val name: String? = " ",
    val ticker: String?= " ",
    val type: String? = " ",
    val locale: String? = "",

    )