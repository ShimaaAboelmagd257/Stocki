package com.example.stocki.data.remoteDatabase

import android.util.Log
import com.example.stocki.data.pojos.*
import com.example.stocki.data.pojos.marketData.ExponintialMovingAverage
import com.example.stocki.data.pojos.marketData.MovingAverageDivergence
import com.example.stocki.data.pojos.marketData.RelativeStengthIndex
import com.example.stocki.data.pojos.marketData.SimpleMovingAverage
import com.example.stocki.data.pojos.refrenceData.ConditionResponse
import com.example.stocki.data.pojos.refrenceData.DividendsResponse
import com.example.stocki.data.pojos.refrenceData.Exchange
import com.example.stocki.data.pojos.refrenceData.FinancialsResponse
import com.example.stocki.utility.Constans
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockiClient @Inject  constructor(): RemoteSource {


    private val stockiService: StockiService by lazy {
        RetrofitHelper.retrofitInstance.create(StockiService::class.java)
    }
    private val apiKey: String = Constans.Api_Key
    private val adjusted:Boolean = Constans.ADUJTED
    private val order:String  = Constans.ORDER
    private val seriesType :String = Constans.SERIES_TYPE
    private val window:Int = Constans.WINDOW
    private val  shortWindow: Int = Constans.SHORT_WINDOW
    private val longWindow: Int = Constans.LONG_WINDOW
    private val  signalWindow: Int = Constans.SIGNAL_WINDOW
    private val timespan:String = Constans.TIMESPAN


    companion object {
        @Volatile
        private var INSTANCE: StockiClient? = null

        fun getInstance(): StockiClient {
            return INSTANCE ?: synchronized(this) {
                val instance = StockiClient()
                INSTANCE = instance
                instance
            }
        }
    }

    override suspend fun getAggregateBars(
        stocksTicker: String,
        multiplier: Int,
        timespan: String,
        from: String,
        to: String
    ): PolygonApiResponse {
        return stockiService.getAggregateBars(stocksTicker, multiplier, timespan, from, to, "desc" ,apiKey)
    }

    override suspend fun getGroupedDailyBars(date: String): GroupedDailyBars {
        return stockiService.getGroupedDailyBars(date)
    }

    override suspend fun getDailyOpenClosePrices(
        stocksTicker: String,
        date: String
    ): DailyOpenCloseResponse {
        return stockiService.getDailyOpenClosePrices(stocksTicker, date, apiKey)
    }

    override suspend fun getPreviousClose(stocksTicker: String): PolygonApiResponse {
        return stockiService.getPreviousClose(stocksTicker, apiKey)
    }
/*@GET("/v3/reference/tickers")
    suspend fun getTicker(
        @Query("ticker") ticker: String
        @Query("type") type: String
        @Query("market") market: String
        @Query("exchange") exchange: String
        @Query("cusip") cusip: String
        @Query("cik") cik: String = "", // Specify the CIK code
        @Query("date") date: String = "", // Specify a point in time
        @Query("search") search: String = "", // Search for terms within ticker and/or company name
        @Query("active") active: Boolean = true, // Specify if tickers should be actively traded
        @Query("limit") limit: Int = 100, // Limit the number of results
        @Query("sort") sort: String = "" ,// Sort field used for ordering
        @Query("apiKey") apiKey: String
    ):TickerResponse
*/
    override suspend fun getTicker(
    //ticker: String,
    type:String
    /*,market: String ,exchange: String ,cusip: String ,cik: String,date: String
                                   ,search: String,sort: String*/): TickerResponse {
        return stockiService.getTicker(type,apiKey)
    }

    override suspend fun getTickerInfo(ticker: String): CompanyResponse {
        Log.d("StockiClient", "getTickerInfo  ${ticker} ${ stockiService.getTickerInfo(ticker, apiKey)}" )

        return stockiService.getTickerInfo(ticker, apiKey)
    }

    override suspend fun getTickerEvents(id: String): TickerEventsResponse {
        return stockiService.getTickerEvents(id, apiKey)
    }

    override suspend fun getTickerNews(): NewsResponse {
        return stockiService.getTickerNews(apiKey)
    }

    override suspend fun getTickerTypes(
        assetClass: String,
        locale: String
    ): TickerTypesResponse {
        return stockiService.getTickerTypes(assetClass, locale, apiKey)
    }

    override suspend fun getMarketHolidays(): List<MarketHoliday> {
        return stockiService.getMarketHolidays(apiKey)
    }

    override suspend fun getMarketStatus(): MarketStatusResponse {
        return stockiService.getMarketStatus(apiKey)
    }

    override suspend fun getStockSplits(): StockSplitResponse {
        return stockiService.getStockSplits(apiKey)
    }

    override suspend fun getDividends(): DividendsResponse {
        return stockiService.getDividends(apiKey)
    }

    override suspend fun getStockFinancial(ticker: String): FinancialsResponse {
        return stockiService.getStockFinancial(ticker, apiKey)
    }

    override suspend fun getApiCondition(): ConditionResponse {
        return stockiService.getApiCondition(apiKey)
    }

    override suspend fun getExchanges(): Exchange {
        return stockiService.getExchanges(apiKey)
    }

    override suspend fun getSMA(
        stockTicker: String,
    ): SimpleMovingAverage {
        Log.d("StockiClint", "getSMA ${stockiService.getSMV(  stockTicker,timespan ,adjusted ,window,seriesType,order,apiKey)} ")

        return stockiService.getSMV(  stockTicker,timespan ,adjusted ,window,seriesType,order,apiKey)
    }

    override suspend fun getEMA(
        stockTicker: String,
    ): ExponintialMovingAverage {
        return stockiService.getEMA(stockTicker,timespan , adjusted ,window,seriesType,order,apiKey)
    }

    override suspend fun getMACD(
        stockTicker: String
    ): MovingAverageDivergence {
           return stockiService.getMACD(stockTicker,timespan,adjusted,shortWindow,longWindow,signalWindow,seriesType,order,apiKey)
    }

    override suspend fun getRSI(
        stockTicker: String
    ): RelativeStengthIndex {
        return stockiService.getRSI(stockTicker,timespan , adjusted ,window,seriesType,order,apiKey)
    }

}
/*override suspend fun getDividends(
       /* exDividendDate: String,
        recordDate: String,
        declarationDate: String,
        payDate: String,
        cashAmount: Int,
        dividendType: String,
        limit: Int,
        sort: String,*/
        apiKey: String
    ): DividendsResponse {
        return  stockiService.getDividends(/*exDividendDate,recordDate,declarationDate,payDate,cashAmount,dividendType,limit,sort,*/apiKey)  }
*/