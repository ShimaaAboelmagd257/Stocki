package com.example.stocki.market.status

import com.example.stocki.data.repository.Repository
import com.example.stocki.feeds.FeedStates
import javax.inject.Inject

class StatusUseCase @Inject constructor(val repository: Repository) {

    suspend operator fun invoke(): StatusState {
        val data = repository.getMarketStatus()
        return StatusState.Data(data)
    }
}