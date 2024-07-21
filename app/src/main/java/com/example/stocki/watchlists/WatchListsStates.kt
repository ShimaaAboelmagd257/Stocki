package com.example.stocki.watchlists

import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.WatchList

sealed class WatchListsStates {
    object Loading : WatchListsStates()
    data class Data(val data: List<WatchList>?) : WatchListsStates()
    data class Deleting(val deleting :String) :WatchListsStates()
    data class Success(val message: String) : WatchListsStates()
    data class Error(val error: String) : WatchListsStates()
}