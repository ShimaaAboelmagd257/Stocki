package com.example.stocki.data.remoteDatabase

import com.example.stocki.data.pojos.*
import com.example.stocki.data.pojos.marketData.ExponintialMovingAverage
import com.example.stocki.data.pojos.marketData.MovingAverageDivergence
import com.example.stocki.data.pojos.marketData.RelativeStengthIndex
import com.example.stocki.data.pojos.marketData.SimpleMovingAverage
import com.example.stocki.data.pojos.refrenceData.ConditionResponse
import com.example.stocki.data.pojos.refrenceData.DividendsResponse
import com.example.stocki.data.pojos.refrenceData.Exchange
import com.example.stocki.data.pojos.refrenceData.FinancialsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface RemoteSource {

    suspend fun getAggregateBars(
        stocksTicker: String,
        multiplier: Int,
        timespan: String,
        from: String,
        to: String
    ): PolygonApiResponse

    suspend fun getGroupedDailyBars(date: String): GroupedDailyBars

    suspend fun getDailyOpenClosePrices(
        stocksTicker: String,
        date: String
    ): DailyOpenCloseResponse

    suspend fun getPreviousClose(stocksTicker: String): PolygonApiResponse
    suspend fun getTicker(
      //  ticker: String,
          type:String
        //  ,market: String ,exchange: String ,cusip: String ,cik: String,date: String
                       //   ,search: String,sort: String
    ): TickerResponse

    suspend fun getTickerInfo(
        ticker: String
    ): CompanyResponse


    suspend fun getTickerEvents(
      id: String
    ): TickerEventsResponse

    suspend fun getTickerNews(
    ): NewsResponse

    suspend fun getTickerTypes(
         assetClass: String = "stocks", // Default value for asset_class parameter
        locale: String = "us" // Default value for locale parameter
    ): TickerTypesResponse


    suspend fun  getMarketHolidays(
    ): List<MarketHoliday>

    suspend fun getMarketStatus(
    ): MarketStatusResponse

    suspend fun getStockSplits(
    ): StockSplitResponse


    suspend fun getDividends(
       /*  exDividendDate: String,
       recordDate: String,
        declarationDate: String,
       payDate: String,
       cashAmount: Int,
        dividendType: String,
         limit: Int,
         sort: String,*/
    ): DividendsResponse

    suspend fun getStockFinancial(
         ticker: String,
    ): FinancialsResponse

    suspend fun getApiCondition(
    ): ConditionResponse

    suspend fun getExchanges(
    ): Exchange

    suspend fun getSMA(
         stockTicker: String
    ): SimpleMovingAverage

    suspend fun getEMA(
         stockTicker: String
    ): ExponintialMovingAverage

    suspend fun getMACD(
         stockTicker: String
    ): MovingAverageDivergence

    suspend fun getRSI(
         stockTicker: String
    ): RelativeStengthIndex
}
