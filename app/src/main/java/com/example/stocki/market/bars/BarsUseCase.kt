package com.example.stocki.market.bars

import com.example.stocki.data.repository.Repository
import javax.inject.Inject


class BarsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke( stocksTicker: String,
                                 multiplier: Int,
                                 timespan: String,
                                 from: String,
                                 to: String): BarStates {
        val data = repository.getAggregateBars(stocksTicker,multiplier,timespan,from,to)
        val resultsArray = data.results ?: emptyList()
        return BarStates.Data(resultsArray)
    }
}