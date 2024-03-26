package com.example.stocki.data.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class TickerTypesResponse(
     val count: Int,
    val nextUrl: String?,
     val request_id: String,
     val results: List<TickerType>,
     val status: String
) : Parcelable


@Parcelize
data class TickerType(
    val asset_class: AssetClass,
    val code: String,
    val description: String,
    val locale: Locale,
    val status: String
) : Parcelable

enum class AssetClass {
    stocks,
    options,
    crypto,
    fx,
    indices
}

enum class Locale {
    us,
    global
}