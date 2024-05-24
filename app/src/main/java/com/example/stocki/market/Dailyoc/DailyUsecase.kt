package com.example.stocki.market.Dailyoc

import com.example.stocki.data.repository.Repository
import javax.inject.Inject

class DailyUsecase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(ticker:String , date: String): DailyState {
        val data = repository.getDailyOpenClosePrices(ticker , date)
        return DailyState.Data(data)
    }
}