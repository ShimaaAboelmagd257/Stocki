package com.example.stocki.search

import android.util.Log
import com.example.stocki.data.pojos.TickerTypes
import com.example.stocki.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchUsecase @Inject constructor(private val repository: Repository) {
    /* suspend operator fun invoke(markets: List<String>): List<SearchState> {
    *//*  *//**//*  val data = repository.getTicker(market)
        val resultsArray = data.results
        // Fetch data types and save them locally
        // Map the fetched data to TickerTypes entities
        val tickerTypesList = resultsArray.map { ticker ->
            TickerTypes(
                market = ticker.market,
                name = ticker.name,
                ticker = ticker.ticker,
                type = ticker.type,
                locale = ticker.locale
            )
        }
        repository.insertTypes(tickerTypesList)
        Log.d("SearchUsecase", "insertTypes  ${tickerTypesList.size}" )*//**//*
        val localData = repository.getAllTickerTypes()
        return if (localData.isNotEmpty()) {
            SearchState.Data(localData)
        } else {
            // If local data is empty, fetch from the remote source
            val remoteData = repository.getTicker(market)
            // Save remote data to local database
            repository.insertTypes(remoteData)
            SearchState.Data(remoteData)
        }
        return SearchState.Data(resultsArray)
    }*//*
        val searchStates = mutableListOf<SearchState>()
        markets.forEach { market ->
            val localData = repository.getAllTickerTypes(market)
            val state = if (localData.isNotEmpty()) {
                // Data available locally, fetch it from Room
                SearchState.Data(localData)
            } else {
                // No data locally, fetch from remote source
                fetchDataFromRemote(market)
            }
            searchStates.add(state)
        }
        return searchStates
    }

    private suspend fun fetchDataFromRemote(market: List<TickerTypes>): SearchState {
        return try {
            val remoteData = repository.insertTypes(market)
            repository.insertTypes(remoteData)
            SearchState.Data(remoteData)
        } catch (e: Exception) {
            SearchState.Error(e.message ?: "An error occurred")
        }
    }*/

/*
    suspend operator fun invoke(markets: List<String>): SearchState {
        return withContext(Dispatchers.IO){
        try {
            val localData = repository.getAllTickerTypes()
            val remoteData = mutableListOf<TickerTypes>()

            markets.forEach { market ->
                // Check if data for the market is available locally
                val localMarketData = localData.filter { it.market == market }
                if (localMarketData.isEmpty()) {
                    // Data for the market is empty locally, fetch it from the remote source
                    val data = repository.getTicker(market)
                    remoteData.addAll(data)
                } else {
                    remoteData.addAll(localMarketData) // Add local data to remoteData
                }
            }

            // Insert fetched data into the local Room database
            repository.insertTypes(remoteData)
            SearchState.Data(remoteData)
        } catch (e: Exception) {
            SearchState.Error(e.message ?: "An error occurred")
        }
    }
}
*/
suspend operator fun invoke(markets: List<String>): SearchState {
    return withContext(Dispatchers.IO) {
        try {
            val remoteData = mutableListOf<TickerTypes>()

            markets.forEach { market ->
                // Fetch data for each market
                val data = repository.getTicker(market)
                remoteData.addAll(data)
            }

            // Insert fetched data into the local Room database
            repository.insertTypes(remoteData)
            SearchState.Data(remoteData)
        } catch (e: Exception) {
            SearchState.Error(e.message ?: "An error occurred")
        }
    }
}

}

