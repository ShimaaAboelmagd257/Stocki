package com.example.stocki.search

import android.annotation.SuppressLint
import android.util.Log
import com.example.stocki.data.pojos.TickerTypes
import com.example.stocki.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class SearchUsecase @Inject constructor(private val repository: Repository) {

    @SuppressLint("SuspiciousIndentation")
    suspend operator fun invoke(markets: List<String>): SearchState {
        return withContext(Dispatchers.IO) {
            try {
                // Attempt to fetch data locally
              val localData = repository.getAllTickerTypes()

                if (localData.isNotEmpty()) {
                    // Data found locally, return it
                    SearchState.Data(localData)
                } else {
                    // No local data, fetch remotely
                    val remoteData = mutableListOf<TickerTypes>()

                    markets.forEach { market ->
                        // Fetch data for each market
                        val data = repository.getTicker(market)
                        remoteData.addAll(data)
                        Log.d("SearchUsecase", "remoteData  ${remoteData.size}" )

                    }

                    // Insert fetched data into the local Room database
                    repository.insertTypes(remoteData)
                    SearchState.Data(remoteData)

                 }
            } catch (e: Exception) {
                SearchState.Error(e.message ?: "An error occurred")
            }
        }
    }
}



