package com.example.stocki.data.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GroupedDailyBars(
    val adjusted: Boolean,
    val queryCount: Int,
    val requestId: String,
    val status: String,
    val results: List<AggregateData>
): Parcelable


@Parcelize
data class PolygonApiResponse(
    val adjusted: Boolean,
     val queryCount: Int,
     val requestId: String,
    val results: List<AggregateData>
): Parcelable

@Parcelize
data class DailyOpenCloseResponse(
    val afterHours: Double,
    val close: Double,
    val from: String,
    val high: Double,
    val low: Double,
    val open: Double,
    val otc: Boolean,
    val preMarket: Double,
    val status: String,
    val symbol: String,
    val volume: Long
): Parcelable

@Parcelize
data class AggregateData(
    val T: String, //ticker name
    val c: Double, // Closing price
    val h: Double, // High price
    val l: Double,  // Low price
    val n: Int,   // Number of items in the bar
    val o: Double,  // Opening price
    val t: Long,   // Timestamp
    val v: Double,    // Volume
    val vw: Double  //Volume Weighted Average Price
): Parcelable
/*T*string

The exchange symbol that this item is traded under.
c*number

The close price for the symbol in the given time period.
h*number

The highest price for the symbol in the given time period.
l*number

The lowest price for the symbol in the given time period.
ninteger

The number of transactions in the aggregate window.
o*number

The open price for the symbol in the given time period.
otcboolean

Whether or not this aggregate is for an OTC ticker. This field will be left off if false.
t*integer

The Unix Msec timestamp for the end of the aggregate window.
v*number

The trading volume of the symbol in the given time period.
vwnumber

The volume weighted average price.
*/