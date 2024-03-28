package com.example.stocki.holidays

import com.example.stocki.data.repository.Repository
import javax.inject.Inject

class HolidaysUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): HolidaysStates {
        val data = repository.getMarketHolidays()
        return HolidaysStates.Data(data)
    }
}