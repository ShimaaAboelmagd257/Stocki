package com.example.stocki.data.localDatabase

import com.example.stocki.data.pojos.*

interface localSource {


    fun getgetAllTickerTypes(): List<TickerTypes>
    suspend fun insertTypes(tickerTypes: List<TickerTypes>)


    fun getAllWatchLists(): List<TickerTypes>
    suspend fun insertTicker(types: List<TickerTypes>)
    fun deleteTicker(ticker : TickerTypes)

/*
    fun getAllTickerInfo(): List<Company>
    suspend fun insertTickerInfo(brandingSaved: List<Company>)
*/
    suspend fun insertMarketLocal(aggregateData: List<AggregateData>)
    fun getAllTickersLocal(): List<AggregateData>
    fun getTickerItemById(T:String) : AggregateData

    fun getAllTickerLogo(): List<BrandingSaved>
    suspend fun insertTickerLogo(brandingSaved: List<BrandingSaved>)
    fun getTickerLogo(ticker:String): BrandingSaved

    suspend fun insertNews(newsLocal: List<NewsItem>)
    fun getNews(): List<NewsItem>
    fun getNewsItemById(id:String):NewsItem
}