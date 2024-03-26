package com.example.stocki.feeds

import com.example.stocki.data.pojos.NewsItem

sealed class FeedStates {
    object Loading : FeedStates()
    data class Data(val data: List<NewsItem>?) : FeedStates()
    data class Error(val error: String) : FeedStates()
}