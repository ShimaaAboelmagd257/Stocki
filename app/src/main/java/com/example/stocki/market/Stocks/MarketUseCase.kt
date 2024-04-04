package com.example.stocki.market.Stocks

import com.example.stocki.data.repository.Repository
import javax.inject.Inject

class MarketUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(date: String): MarketState {
        val data = repository.getGroupedDailyBars(date)
        val resultsArray = data.results
        return MarketState.Data(resultsArray)
    }
}