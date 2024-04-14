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
class EmaUsecase @Inject constructor(val repository: Repository) {
    suspend operator fun invoke(stockTicker:String ): EmaState {
        val data = repository.getEMA(stockTicker)
        //  val resultsList = listOf(data)
        return EmaState.Data(data)
    }
}
class MacdUsecase @Inject constructor(val repository: Repository) {
    suspend operator fun invoke(stockTicker:String ): MacdState {
        val data = repository.getMACD(stockTicker)
        //  val resultsList = listOf(data)
        return MacdState.Data(data)
    }
}
class RsiUsecase @Inject constructor(val repository: Repository) {
    suspend operator fun invoke(stockTicker:String ): RsiState {
        val data = repository.getRSI(stockTicker)
        //  val resultsList = listOf(data)
        return RsiState.Data(data)
    }
}