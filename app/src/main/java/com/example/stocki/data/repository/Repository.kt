package com.example.stocki.data.repository

import android.util.Log
import com.example.stocki.data.localDatabase.localSource
import com.example.stocki.data.pojos.*
import com.example.stocki.data.pojos.marketData.ExponintialMovingAverage
import com.example.stocki.data.pojos.marketData.MovingAverageDivergence
import com.example.stocki.data.pojos.marketData.RelativeStengthIndex
import com.example.stocki.data.pojos.marketData.SimpleMovingAverage
import com.example.stocki.data.pojos.refrenceData.ConditionResponse
import com.example.stocki.data.pojos.refrenceData.DividendsResponse
import com.example.stocki.data.pojos.refrenceData.Exchange
import com.example.stocki.data.pojos.refrenceData.FinancialsResponse
import com.example.stocki.data.remoteDatabase.RemoteSource
import com.example.stocki.utility.Constans.currentTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val remoteSource: RemoteSource , private val localSource: localSource) {

 /*   fun getAllTickerInfo(): List<Company>{
        return localSource.getAllTickerInfo()
    }
    suspend fun insertTickerInfo(company: List<Company>){
        company.forEach { ticker ->
            //  Log.d("StockiRepo", "insertTickerLogo: logoUrl = ${ticker.branding.logoUrl}, iconUrl = ${ticker.branding.iconUrl}")
        }
        localSource.insertTickerInfo(company)

    }*/
     suspend fun insertMarketLocal(aggregateData: List<AggregateData>){
         localSource.insertMarketLocal(aggregateData)
     }

    fun getAllTickersLocal(): List<AggregateData>{
        return localSource.getAllTickersLocal()
    }
    suspend fun getTickerItemById(T:String) : AggregateData{
        return withContext(Dispatchers.IO) { localSource.getTickerItemById(T)}
    }
    suspend fun getTickerLogo(ticker : String): BrandingSaved? {
     return withContext(Dispatchers.IO) {
         localSource.getTickerLogo(ticker)
     }
    }
    suspend fun getAllTickerLogo(): List<BrandingSaved>{
                 return withContext(Dispatchers.IO) {
                     localSource.getAllTickerLogo()
                 }
    }
    suspend fun insertTickerLogo(brandingSaved: List<BrandingSaved>){
        brandingSaved.forEach { ticker ->
           Log.d("StockiRepo", "insertTickerLogo: logoUrl = ${ticker.logoUrl}, iconUrl = ${ticker.iconUrl}")
        }
        withContext(Dispatchers.IO) {
            localSource.insertTickerLogo(brandingSaved)
        }

    } fun getAllWatchLists(): List<AggregateData>{
        return localSource.getAllWatchLists()
    }
    suspend fun insertTicker(tickerTypes: AggregateData){
        localSource.insertTicker(tickerTypes)
    }
     fun getTickerById(T: String): AggregateData {
         return localSource.getTickerById(T)
    }
    fun deleteTicker(ticker : AggregateData){
        localSource.deleteTicker(ticker)
    }
    fun getAllTickerTypes(): List<TickerTypes>{
        return localSource.getgetAllTickerTypes()
    }
    suspend fun insertTypes(tickerTypes: List<TickerTypes>){
        tickerTypes.forEach { ticker ->
            Log.d("StockiRepo", "insertTypes: Market = ${ticker.market}, Name = ${ticker.name}, Ticker = ${ticker.ticker}, Type = ${ticker.type}, Locale = ${ticker.locale}")
        }
        localSource.insertTypes(tickerTypes)

    }
    suspend fun getTicker(market: String):  List<TickerTypes>  {
        Log.d("StockiRepo", "getTicker " )
        val remoteSource = remoteSource.getTicker(market)
        return remoteSource.results.map { ticker ->
            TickerTypes(
                market = ticker.market,
                name = ticker.name,
                ticker = ticker.ticker,
                type = ticker.type,
                locale = ticker.locale
            )
        }
    }

    suspend fun getAggregateBars(
        stocksTicker: String,
        multiplier: Int,
        timespan: String,
        from: String,
        to: String,
    ): PolygonApiResponse {
        Log.d("StockiRepo", "getAggregateBars  ${stocksTicker}" )

        return remoteSource.getAggregateBars(stocksTicker, multiplier, timespan, from, to)
    }

    suspend fun getGroupedDailyBars(date: String): List<AggregateData> {
        return  withContext(Dispatchers.IO) {
         /*   val localdata = localSource.getAllTickersLocal()
            if (localdata.isNotEmpty()) {
                Log.d("StockiRepo", "getGroupedDailyBars  Locally")
                localdata
            } else {*/
                val response = remoteSource.getGroupedDailyBars(date)
                localSource.insertMarketLocal(response)
                Log.d("StockiRepo", "getGroupedDailyBars  ${date}")
                response
          //  }
        }
    }

    suspend fun getDailyOpenClosePrices(
        stocksTicker: String,
        date: String

    ): DailyOpenCloseResponse {
        Log.d("StockiRepo", "getDailyOpenClosePrices  ${date} ${stocksTicker}" )

        return remoteSource.getDailyOpenClosePrices(stocksTicker, date)
    }

    suspend fun getPreviousClose(stocksTicker: String): PolygonApiResponse {
        Log.d("StockiRepo", "getPreviousClose  ${stocksTicker}" )

        return remoteSource.getPreviousClose(stocksTicker)
    }


    /* suspend fun getTicker(
            //ticker: String,
                             // type:String,
                              market: String
                              *//*market: String ,exchange: String ,cusip: String ,cik: String,date: String
                          ,search: String,sort: String*//*
    ):  List<TickerTypes> */
    suspend fun getTickerInfo(ticker: String): CompanyResponse {
        Log.d("StockiRepo", "getTickerInfo  ${ticker} ${remoteSource.getTickerInfo(ticker)}" )

        return remoteSource.getTickerInfo(ticker)
    }

    suspend fun getTickerEvents(id: String): TickerEventsResponse {
        Log.d("StockiRepo", "getTickerEvents  ${id}" )

        return remoteSource.getTickerEvents(id)
    }

    suspend fun getTickerNews(): NewsResponse {
        Log.d("StockiRepo", "getTickerNews  " )

        return remoteSource.getTickerNews()
    }

   suspend fun getAllNews():List<NewsItem>{
       return localSource.getNews()
   }
    suspend fun fetchAndStoreTickerNews(): List<NewsItem> = withContext(Dispatchers.IO) {
        val response = remoteSource.getTickerNews().results
        val localNews = response.map {
            NewsItem(
                amp_url = it.amp_url ?: "",
                article_url = it.article_url,
                author = it.author,
                description = it.description ?: "Descibtion Not Found",
                id = it.id,
                image_url = it.image_url,
                published_utc = it.published_utc,
                tickers = it.tickers.first(),
                title = it.title,
                fetched_at = currentTime
            )
        }
        localSource.insertNews(localNews)
        return@withContext localNews
    }
    fun getNewsItemById(id: String): Flow<NewsItem?> {
        return flow {
            val newsItem = localSource.getNewsItemById(id)
            emit(newsItem)
        }.flowOn(Dispatchers.IO)
    }

   /* fun getNewsItemById(id:String):NewsItem{
        return localSource.getNewsItemById(id)
    }*/

     fun getAllnewsTitles() : List<NewsItem>{
       return localSource.getNews()
    }

    suspend fun getTickerTypes(
        assetClass: String ,
        locale: String
    ): TickerTypesResponse {
        Log.d("StockiRepo", "getTickerTypes ")

        return withContext(Dispatchers.IO) {
            remoteSource.getTickerTypes(assetClass, locale)
        }
    }

    suspend fun getMarketHolidays(): List<MarketHoliday> {
        Log.d("StockiRepo", "getMarketHolidays  ")

        return withContext(Dispatchers.IO) {
            remoteSource.getMarketHolidays()
        }
    }

    suspend fun getMarketStatus(): MarketStatusResponse {
        Log.d("StockiRepo", "getMarketStatus ")

        return withContext(Dispatchers.IO) {
            remoteSource.getMarketStatus()
        }
    }

    suspend fun getStockSplits(): StockSplitResponse {
        Log.d("StockiRepo", "getStockSplits")

        return withContext(Dispatchers.IO) {
            remoteSource.getStockSplits()
        }
    }
    suspend fun getDividends(): DividendsResponse {
        Log.d("StockiRepo", "getDividends  ")

        return withContext(Dispatchers.IO) {
            remoteSource.getDividends()
        }
    }

    suspend fun getStockFinancial(ticker: String): FinancialsResponse {
        Log.d("StockiRepo", "getStockFinancial  ${ticker}")

        return withContext(Dispatchers.IO) {
            remoteSource.getStockFinancial(ticker)
        }
    }
    suspend fun getApiCondition(): ConditionResponse {
        Log.d("StockiRepo", "getApiCondition ")

        return withContext(Dispatchers.IO) {
            remoteSource.getApiCondition()
        }
    }

    suspend fun getExchanges(): Exchange {
        Log.d("StockiRepo", "getExchanges ")
        return withContext(Dispatchers.IO) {
            remoteSource.getExchanges()
        }
    }
    suspend fun getSMA(
        stockTicker: String
    ): SimpleMovingAverage {
        return withContext(Dispatchers.IO) {
            remoteSource.getSMA(stockTicker)
        }
    }
    suspend fun getEMA(
        stockTicker: String
    ): ExponintialMovingAverage {
        return withContext(Dispatchers.IO) { remoteSource.getEMA(stockTicker)

        }
    }
    suspend fun getMACD(
        stockTicker: String
    ): MovingAverageDivergence {
        return withContext(Dispatchers.IO) {
            remoteSource.getMACD(stockTicker)
        }
    }
    suspend fun getRSI(
        stockTicker: String
    ): RelativeStengthIndex {
        return withContext(Dispatchers.IO) {
            remoteSource.getRSI(stockTicker)
        }
    }
}
