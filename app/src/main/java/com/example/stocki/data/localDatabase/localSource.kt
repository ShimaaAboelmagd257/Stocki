package com.example.stocki.data.localDatabase

import com.example.stocki.data.pojos.TickerTypes

interface localSource {


    fun getgetAllTickerTypes(/*market: String*/): List<TickerTypes>
    suspend fun insertTypes(tickerTypes: List<TickerTypes>)
}