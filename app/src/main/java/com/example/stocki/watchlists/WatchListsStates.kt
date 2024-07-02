package com.example.stocki.watchlists

import com.example.stocki.data.pojos.AggregateData

sealed class WatchListsStates {
    object Loading : WatchListsStates()
    data class Data(val data: List<AggregateData>?) : WatchListsStates()
    data class Deleting(val deleting :String) :WatchListsStates()
    data class Success(val message: String) : WatchListsStates()
    data class Error(val error: String) : WatchListsStates()
}