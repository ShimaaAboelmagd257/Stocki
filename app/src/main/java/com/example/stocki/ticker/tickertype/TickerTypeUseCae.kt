package com.example.stocki.ticker.tickertype

import com.example.stocki.data.repository.Repository
import javax.inject.Inject

class TickerTypeUseCae @Inject constructor(val repository: Repository) {

    suspend operator fun invoke(ticker:String,locale : String ): TickerTypeState {
        val data = repository.getTickerTypes(ticker,locale)
        val resultsArray = data.results ?: emptyList()
        return TickerTypeState.Data(resultsArray)
    }
}