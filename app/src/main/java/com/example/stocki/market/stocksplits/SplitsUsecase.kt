package com.example.stocki.market.stocksplits

import com.example.stocki.data.repository.Repository
import javax.inject.Inject

class SplitsUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): SplitsState {
        val data = repository.getStockSplits()
        val resultsArray = data.results
        return SplitsState.Data(resultsArray)
    }
}