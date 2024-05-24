package com.example.stocki.data.pojos

import android.os.Parcelable

import kotlinx.parcelize.Parcelize


@Parcelize
data class NewsResponse(
    val count: Int,
    // val nextUrl: String,
     val requestId: String,
    val results: List<NewsItem>,
    val status: String
) : Parcelable

@Parcelize
data class NewsItem(
    val amp_url: String,
    val article_url: String,
    val author: String,
    val description: String,
    val id: String,
    val image_url: String,
    val keywords: List<String>,
    val published_utc: String,
    val publisher: Publisher,
    val tickers: List<String>,
    val title: String
) : Parcelable

@Parcelize
data class Publisher(
     val favicon_url: String,
     val homepage_url: String,
     val logo_url: String,
    val name: String
) : Parcelable