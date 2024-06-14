package com.example.stocki.feeds

import com.example.stocki.data.pojos.NewsItem
import com.example.stocki.data.pojos.NewsItemResponse
import kotlinx.coroutines.flow.Flow

sealed class FeedStates {
    object Loading : FeedStates()
    data class newsItemInfo(val newsItem: Flow<NewsItem?>) :FeedStates()
    data class Data(val data: List<NewsItem>?) : FeedStates()
//    data class LocalData(val data:List<NewsItem>):FeedStates()
    data class Error(val error: String) : FeedStates()
}