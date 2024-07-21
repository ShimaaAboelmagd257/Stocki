package com.example.stocki.data.localDatabase

import com.example.stocki.data.pojos.*

interface localSource {


/*
    fun getgetAllTickerTypes(): List<TickerTypes>
    suspend fun insertTypes(tickerTypes: List<TickerTypes>)

*/

    suspend fun getAllWatchLists(): List<WatchList>
    suspend fun insertTicker(types: WatchList)
    suspend  fun deleteTicker(ticker : WatchList)
    suspend  fun getTickerById(T:String) : WatchList




/*
    fun getAllTickerInfo(): List<Company>
    suspend fun insertTickerInfo(brandingSaved: List<Company>)
*/
    suspend fun insertMarketLocal(aggregateData: List<AggregateData>)
    suspend fun getAllTickersLocal(): List<AggregateData>
    fun getTickerItemById(T:String) : AggregateData
    fun deleteMarketLocal(expiryTime: Long)

  /*  fun getAllTickerLogo(): List<BrandingSaved>
    suspend fun insertTickerLogo(brandingSaved: List<BrandingSaved>)
    fun getTickerLogo(ticker:String): BrandingSaved*/

    suspend fun insertNews(newsLocal: List<NewsItem>)
    fun getNews(): List<NewsItem>
    fun getNewsItemById(id:String):NewsItem
    fun deleteOldNews(newsItems: List<NewsItem>)

}