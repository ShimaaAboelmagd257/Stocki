package com.example.stocki.ticker.technicalIndicator
import com.example.stocki.data.pojos.marketData.ExponintialMovingAverage
import com.example.stocki.data.pojos.marketData.MovingAverageDivergence
import com.example.stocki.data.pojos.marketData.RelativeStengthIndex
import com.example.stocki.data.pojos.marketData.SimpleMovingAverage

sealed class SmaState {
    object Loading : SmaState()
    data class Data(val data: SimpleMovingAverage) : SmaState()
    data class Error(val error: String) : SmaState()
}
sealed class EmaState{
    object Loading : EmaState()
    data class Data(val data: ExponintialMovingAverage) : EmaState()
    data class Error(val error: String) : EmaState()
}
sealed class MacdState{
    object Loading : MacdState()
    data class Data(val data: MovingAverageDivergence) : MacdState()
    data class Error(val error: String) : MacdState()
}
sealed class RsiState{
    object Loading : RsiState()
    data class Data(val data: RelativeStengthIndex) : RsiState()
    data class Error(val error: String) : RsiState()
}