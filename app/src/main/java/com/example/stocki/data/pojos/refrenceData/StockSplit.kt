package com.example.stocki.data.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockSplitResponse(
    val nextUrl: String?,
    val results: List<StockSplit>,
    val status: String
) : Parcelable

@Parcelize
data class StockSplit(
     val executionDate: String,
    val splitFrom: Int,
    val splitTo: Int,
   val ticker: String
) : Parcelable
