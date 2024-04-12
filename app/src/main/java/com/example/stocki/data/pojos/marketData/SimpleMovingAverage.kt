package com.example.stocki.data.pojos.marketData

import com.example.stocki.data.pojos.AggregateData
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
@Parcelize
data class Underlying(
    val aggregates: List<Aggregates>,
    val url: String
):Parcelable
@Parcelize

data class Value(
    val timestamp: Long,
    val value: Double
):Parcelable
@Parcelize

data class Results(
    val underlying: Underlying,
    val values: List<Value>
):Parcelable
@Parcelize

data class SimpleMovingAverage(
    val next_url: String,
    val request_id: String,
    val results: Results,
    val status: String
):Parcelable

@Parcelize
data class Aggregates(
    val c: Double, // Closing price
    val h: Double, // High price
    val l: Double,  // Low price
    val n: Int,   // Number of items in the bar
    val o: Double,  // Opening price
    val t: Long,   // Timestamp
    val v: Double,    // Volume
    val vw: Double  //Volume Weighted Average Price
): Parcelable

*/
/*data class ApiResponse(
    val next_url: String?,
    val request_id: String?,
    val results: Results?,
    val status: String?
)

data class Results(
    val underlying: Underlying?,
    val values: List<Value>?
)

data class Underlying(
    val aggregates: List<Aggregate>?,
    val url: String?
)

data class Aggregate(
    val c: Double, // Closing price
    val h: Double, // Highest price
    val l: Double, // Lowest price
    val n: Int, // Number of transactions
    val o: Double, // Open price
    val otc: Boolean?, // OTC flag
    val t: Long, // Unix Msec timestamp
    val v: Double, // Trading volume
    val vw: Double // Volume weighted average price
)

data class Value(
    val timestamp: Long, // Unix Msec timestamp
    val value: Double // Indicator value
)
*/
@Parcelize
data class Underlying(
    val aggregates: List<AggregateData>,
    val url: String?

): Parcelable

@Parcelize
data class Value(
    val timestamp: Long,
    val value: Double
): Parcelable

@Parcelize
data class Results(
    val underlying: Underlying,
    val values: List<Value>
): Parcelable

@Parcelize
data class SimpleMovingAverage(
    val next_url: String,
    val request_id: String,
    val results: Results,
    val status: String
): Parcelable

@Parcelize
data class Aggregate(
    val c: Double, // Closing price
    val h: Double, // Highest price
    val l: Double, // Lowest price
    val n: Int, // Number of transactions
    val o: Double, // Open price
    val otc: Boolean?, // OTC flag
    val t: Long, // Unix Msec timestamp
    val v: Double, // Trading volume
    val vw: Double // Volume weighted average price
): Parcelable

