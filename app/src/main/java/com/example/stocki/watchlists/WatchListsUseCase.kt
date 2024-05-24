package com.example.stocki.watchlists

import com.example.stocki.data.pojos.TickerTypes
import com.example.stocki.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WatchListsUseCase @Inject constructor(val repository: Repository) {

    suspend  fun getAllWatchList(): WatchListsStates {
        return withContext(Dispatchers.IO) {
            try {
                val data = repository.getAllWatchLists()
                WatchListsStates.Data(data)
            } catch (e:Error){
                WatchListsStates.Error(e.message ?: "An error occurred")
            }
        }


    }
    fun deleteWatchTicker(ticker : TickerTypes){
        repository.deleteTicker(ticker)
    }
    suspend  fun insertWatchList(watchList : List<TickerTypes>){
        return withContext(Dispatchers.IO) {
            try {
                repository.insertTicker(watchList)
            } catch (e:Error){
                WatchListsStates.Error(e.message ?: "An error occurred")
            }
        }


    }

}