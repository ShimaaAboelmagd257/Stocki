package com.example.stocki.market.stocks

import com.example.stocki.data.pojos.BrandingSaved
import com.example.stocki.data.repository.Repository
import com.example.stocki.ticker.tickerinfo.TickerInfoState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarketUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(date: String): MarketState {
        return withContext(Dispatchers.IO) {
            val data = repository.getGroupedDailyBars(date)
            MarketState.Data(data)

        }
    }

    suspend  fun getTickerLogo( ticker:String): BrandingSaved? {
        return withContext(Dispatchers.IO) {
            val data = repository.getTickerLogo(ticker)
            data
        }
    }
}