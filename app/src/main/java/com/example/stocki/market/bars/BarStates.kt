package com.example.stocki.market.bars

import com.example.stocki.data.pojos.AggregateData

sealed class BarStates {
    object Loading : BarStates()
    data class Data(val data: List<AggregateData>?) : BarStates()
  data class Error(val error: String) : BarStates()
}
