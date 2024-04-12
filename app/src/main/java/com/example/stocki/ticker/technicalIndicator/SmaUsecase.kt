package com.example.stocki.ticker.technicalIndicator

import com.example.stocki.data.repository.Repository
import javax.inject.Inject

class SmaUsecase @Inject constructor(val repository: Repository) {
    suspend operator fun invoke(stockTicker:String /*, timeSpan: String */): SmaState {
        val data = repository.getSMA(stockTicker/*,timeSpan*/)
      //  val resultsList = listOf(data)
        return SmaState.Data(data)
    }
}