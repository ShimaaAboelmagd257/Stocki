package com.example.stocki.data.repository

import android.util.Log
import com.example.stocki.data.localDatabase.localSource
import com.example.stocki.data.pojos.*
import com.example.stocki.data.pojos.refrenceData.ConditionResponse
import com.example.stocki.data.pojos.refrenceData.DividendsResponse
import com.example.stocki.data.pojos.refrenceData.Exchange
import com.example.stocki.data.pojos.refrenceData.FinancialsResponse
import com.example.stocki.data.remoteDatabase.RemoteSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Repository @Inject constructor(private val remoteSource: RemoteSource , private val localSource: localSource) {

    fun getAllTickerTypes(/*market: String*/): List<TickerTypes>{
        return localSource.getgetAllTickerTypes(/*market*/)
    }
    suspend fun insertTypes(tickerTypes: List<TickerTypes>){
        localSource.insertTypes(tickerTypes)
        Log.d("StockiRepo", "insertTypes  ${tickerTypes}" )

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

    suspend fun getGroupedDailyBars(date: String): GroupedDailyBars {
        Log.d("StockiRepo", "getGroupedDailyBars  ${date}" )

        return remoteSource.getGroupedDailyBars(date)
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

    suspend fun getTicker(
        //ticker: String,
                         // type:String,
                          market: String
                          /*market: String ,exchange: String ,cusip: String ,cik: String,date: String
                          ,search: String,sort: String*/
    ):  List<TickerTypes>  {
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

    suspend fun getTickerTypes(
        assetClass: String ,
        locale: String
    ): TickerTypesResponse {
        Log.d("StockiRepo", "getTickerTypes " )

        return remoteSource.getTickerTypes(assetClass, locale)
    }

    suspend fun getMarketHolidays(): List<MarketHoliday> {
        Log.d("StockiRepo", "getMarketHolidays  " )

        return remoteSource.getMarketHolidays()
    }

    suspend fun getMarketStatus(): MarketStatusResponse {
        Log.d("StockiRepo", "getMarketStatus " )

        return remoteSource.getMarketStatus()
    }

    suspend fun getStockSplits(): StockSplitResponse {
        Log.d("StockiRepo", "getStockSplits" )

        return remoteSource.getStockSplits()
    }

    suspend fun getDividends(): DividendsResponse {
        Log.d("StockiRepo", "getDividends  " )

        return remoteSource.getDividends()
    }

    suspend fun getStockFinancial(ticker: String): FinancialsResponse {
        Log.d("StockiRepo", "getStockFinancial  ${ticker}" )

        return remoteSource.getStockFinancial(ticker )
    }

    suspend fun getApiCondition(): ConditionResponse {
        Log.d("StockiRepo", "getApiCondition " )

        return remoteSource.getApiCondition()
    }

    suspend fun getExchanges(): Exchange {
        Log.d("StockiRepo", "getExchanges " )
        return remoteSource.getExchanges()
    }
}
