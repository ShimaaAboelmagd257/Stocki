package com.example.stocki.data.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TickerEventsResponse(
    val requestId: String,
    val results: TickerEvents,
    val status: String
): Parcelable

@Parcelize
data class TickerEvents(
    val events: List<TickerEvent>,
    val name: String
): Parcelable

@Parcelize
data class TickerEvent(
    val date: String,
    val tickerChange: TickerChange,
    val type: String
): Parcelable

@Parcelize
data class TickerChange(
    val ticker: String
): Parcelable
