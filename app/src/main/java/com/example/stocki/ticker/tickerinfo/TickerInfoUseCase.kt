package com.example.stocki.ticker.tickerinfo

import com.example.stocki.data.repository.Repository
import com.example.stocki.ticker.tickertype.TickerTypeState
import javax.inject.Inject

class TickerInfoUseCase @Inject constructor(val repository: Repository) {

    suspend operator fun invoke(ticker:String ): TickerInfoState {
        val data = repository.getTickerInfo(ticker)
        val resultsList = listOf(data.results)
        return TickerInfoState.Data(resultsList)
    }
}