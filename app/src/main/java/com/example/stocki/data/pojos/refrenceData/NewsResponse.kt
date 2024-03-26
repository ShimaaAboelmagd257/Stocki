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
    val ampUrl: String,
    val articleUrl: String,
    val author: String,
    val description: String,
    val id: String,
    val imageUrl: String,
    val keywords: List<String>,
    val publishedUtc: String,
    val publisher: Publisher,
    val tickers: List<String>,
    val title: String
) : Parcelable

@Parcelize
data class Publisher(
     val faviconUrl: String,
     val homepageUrl: String,
     val logoUrl: String,
    val name: String
) : Parcelable