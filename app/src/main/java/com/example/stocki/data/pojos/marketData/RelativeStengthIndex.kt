package com.example.stocki.data.pojos.marketData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class RSIUnderlying(
    val url: String
):Parcelable
@Parcelize

data class RSIValue(
    val timestamp: Long,
    val value: Double
):Parcelable
@Parcelize

data class RSIResults(
    val underlying: RSIUnderlying?,
    val values: List<RSIValue>
):Parcelable
@Parcelize

data class RelativeStengthIndex(
    val next_url: String?,
    val request_id: String,
    val results: RSIResults,
    val status: String
): Parcelable
