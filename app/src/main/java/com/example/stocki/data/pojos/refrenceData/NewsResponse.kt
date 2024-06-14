package com.example.stocki.data.pojos

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize


@Parcelize
data class NewsResponse(
    val count: Int,
    // val nextUrl: String,
     val requestId: String,
    val results: List<NewsItemResponse>,
    val status: String
) : Parcelable

@Entity(tableName = "newsLocal")
@Parcelize
data class NewsItem(
    val amp_url: String,
    val article_url: String,
    val author: String,
    val description: String,
    @PrimaryKey
    val id: String,
    val image_url: String,
    /*@TypeConverters(StringListConverter::class) // Use TypeConverters for lists
    val keywords: List<String>,*/
    val published_utc: String,
    /*@TypeConverters(PublisherConverter::class) // Use TypeConverters for custom objects
    val publisher: Publisher,
   // @TypeConverters(StringListConverter::class) // Use TypeConverters for lists*/
    val tickers: String,
    val title: String,
    val fetched_at :Long
):Parcelable

// Example TypeConverter for List<String>
class StringListConverter {
    @TypeConverter
    fun fromString(value: String): List<String> {
        return value.split(",")
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        return list.joinToString(",")
    }
}

// Example TypeConverter for custom object Publisher
class PublisherConverter {
    @TypeConverter
    fun fromPublisher(publisher: Publisher): String {
        // Convert Publisher object to JSON string
        return Gson().toJson(publisher)
    }

    @TypeConverter
    fun toPublisher(json: String): Publisher {
        // Convert JSON string to Publisher object
        return Gson().fromJson(json, Publisher::class.java)
    }
}
@Parcelize
data class NewsItemResponse(
    val amp_url: String,
    val article_url: String,
    val author: String,
    val description: String?,
    val id: String,
    val image_url: String,
    val keywords: List<String>,
    val published_utc: String,
      val publisher: Publisher,
    val tickers:List<String>,
    val title: String
) : Parcelable

@Parcelize
data class Publisher(
     val favicon_url: String,
     val homepage_url: String,
     val logo_url: String,
    val name: String
) : Parcelable

/*

@Entity(tableName = "newsLocal")
@Parcelize
data class newsLocal(
    val id:Long = 0,
    val image_url: String,
    val title: String
) : Parcelable
*/
