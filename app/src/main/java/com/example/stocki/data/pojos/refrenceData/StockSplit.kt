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
     val execution_date: String,
    val split_from: Int,
    val split_to: Float,
   val ticker: String
) : Parcelable
