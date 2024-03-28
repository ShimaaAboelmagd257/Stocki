package com.example.stocki.holidays

import com.example.stocki.data.pojos.MarketHoliday

sealed class HolidaysStates {
    object Loading : HolidaysStates()
    data class Data(val data: List<MarketHoliday>?) : HolidaysStates()
    data class Error(val error: String) : HolidaysStates()
}