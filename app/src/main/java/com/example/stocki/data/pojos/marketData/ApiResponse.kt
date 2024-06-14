package com.example.stocki.data.pojos

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
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

@Entity(tableName = "AggregateData")
@Parcelize
data class AggregateData(
    @PrimaryKey(autoGenerate = true)
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




