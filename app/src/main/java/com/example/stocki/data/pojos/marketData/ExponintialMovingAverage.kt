package com.example.stocki.data.pojos.marketData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize

data class EXAUnderlying(
    val url: String
):Parcelable
@Parcelize

data class EXAValue(
    val timestamp: Long,
    val value: Double
):Parcelable
@Parcelize

data class EXAResults(
    val underlying: EXAUnderlying?,
    val values: List<EXAValue>
):Parcelable
@Parcelize

data class ExponintialMovingAverage(
    val next_url: String?,
    val request_id: String,
    val results: EXAResults,
    val status: String
): Parcelable
