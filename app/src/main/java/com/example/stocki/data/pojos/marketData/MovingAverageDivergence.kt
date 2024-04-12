package com.example.stocki.data.pojos.marketData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize

data class MADUnderlying(
    val url: String
): Parcelable
@Parcelize

data class MADValue(
    val histogram: Double?,
    val signal: Double?,
    val timestamp: Long,
    val value: Double
):Parcelable
@Parcelize

data class MADResults(
    val underlying: MADUnderlying?,
    val values: List<MADValue>
):Parcelable
@Parcelize

data class MovingAverageDivergence(
    val next_url: String?,
    val request_id: String,
    val results: MADResults,
    val status: String
):Parcelable
