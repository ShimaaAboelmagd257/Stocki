package com.example.stocki.feeds

import com.example.stocki.data.repository.Repository
import javax.inject.Inject

class FeedsUseCase @Inject constructor( val repository: Repository) {

    suspend operator fun invoke(): FeedStates {
        val data = repository.getTickerNews()
        val resultsArray = data.results ?: emptyList()
        return FeedStates.Data(resultsArray)
    }
}