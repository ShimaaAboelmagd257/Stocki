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

interface StockiService {

    @GET("/v2/aggs/ticker/{stocksTicker}/range/{multiplier}/{timespan}/{from}/{to}")
    suspend fun getAggregateBars(
        @Path("stocksTicker") stocksTicker: String,
        @Path("multiplier") multiplier: Int,
        @Path("timespan") timespan: String,
        @Path("from") from: String,
        @Path("to") to: String,
        @Query("order") order: String,
        @Query("apiKey") apiKey: String
    ): PolygonApiResponse

    @GET("/v2/aggs/grouped/locale/us/market/stocks/{date}")
    suspend fun getGroupedDailyBars(
        @Path("date") date: String,
       // @Query("apiKey") apiKey: String
    ): GroupedDailyBars
///v2/aggs/grouped/locale/us/market/stocks/{date}
    @GET("/v1/open-close/{stocksTicker}/{date}")
    suspend fun getDailyOpenClosePrices(
        @Path("stocksTicker") stocksTicker: String,
        @Path("date") date: String,
        @Query("apiKey") apiKey: String
    ): DailyOpenCloseResponse

    @GET("/v2/aggs/ticker/{stocksTicker}/prev")
    suspend fun getPreviousClose(
        @Path("stocksTicker") stocksTicker: String,
        @Query("apiKey") apiKey: String
    ):PolygonApiResponse

    @GET("/v3/reference/tickers")
    suspend fun getTicker(
       // @Query("ticker") ticker: String = "", // Specify a ticker symbol
     //   @Query("type") type: String = "", // Specify the type of tickers
       @Query("market") market: String, // Filter by market type
      /*  @Query("exchange") exchange: String = "", // Specify the primary exchange
        @Query("cusip") cusip: String = "", // Specify the CUSIP code
        @Query("cik") cik: String = "", // Specify the CIK code
        @Query("date") date: String = "", // Specify a point in time
        @Query("search") search: String = "", // Search for terms within ticker and/or company name
        @Query("active") active: Boolean = true, // Specify if tickers should be actively traded */
        @Query("limit") limit: Int ,
        //@Query("sort") sort: String = "" ,// Sort field used for ordering*/
        @Query("apiKey") apiKey: String
    ):TickerResponse

    @GET("/v3/reference/tickers/{ticker}")
    suspend fun getTickerInfo(
        @Path("ticker") ticker: String,
        @Query("apiKey") apiKey: String
    ): CompanyResponse


    @GET("/vX/reference/tickers/{id}/events")
    suspend fun getTickerEvents(
        @Query("id") id: String,
        @Query("apiKey") apiKey: String
    ):TickerEventsResponse

    @GET("/v2/reference/news")
    suspend fun getTickerNews(
        @Query("limit") limit: Int = 20,
        @Query("apiKey") apiKey: String
    ):NewsResponse

    @GET("/v3/reference/tickers/types")
    suspend fun getTickerTypes(
        @Query("asset_class") assetClass: String = "stocks", // Default value for asset_class parameter
        @Query("locale") locale: String = "us", // Default value for locale parameter
        @Query("apiKey") apiKey: String
    ):TickerTypesResponse


    @GET("/v1/marketstatus/upcoming")
    suspend fun  getMarketHolidays(
        @Query("apiKey") apiKey: String
    ): List<MarketHoliday>

    @GET("/v1/marketstatus/now")
    suspend fun getMarketStatus(
        @Query("apiKey") apiKey: String
    ): MarketStatusResponse

    @GET("/v3/reference/splits")
    suspend fun getStockSplits(
        @Query("apiKey") apiKey: String
    ): StockSplitResponse
    @GET("/v3/reference/dividends")
    suspend fun getDividends(
        @Query("apiKey") apiKey: String
    ):DividendsResponse

   /* @GET("/v3/reference/dividends")
    suspend fun getDividends(
        @Query("ex_dividend_date") exDividendDate: String,
        @Query("record_date") recordDate: String,
        @Query("declaration_date") declarationDate: String,
        @Query("pay_date") payDate: String,
        @Query("cash_amount") cashAmount: Int,
        @Query("dividend_type") dividendType: String,
        @Query("limit") limit: Int,
        @Query("sort") sort: String,
        @Query("apiKey") apiKey: String
    ):DividendsResponse*/

    @GET("/v3/reference/conditions")
    suspend fun getStockFinancial(
        @Query("ticker") ticker: String,
        @Query("apiKey") apiKey: String
    ):FinancialsResponse

    @GET("/v3/reference/conditions")
    suspend fun getApiCondition(

        @Query("apiKey") apiKey: String
    ):ConditionResponse

    @GET("/v3/reference/exchanges")
    suspend fun getExchanges(
        @Query("apiKey") apiKey: String
    ): Exchange

    @GET("/v1/indicators/sma/{stockTicker}")
    suspend fun getSMV(
        @Path("stockTicker") stockTicker: String,
    //    @Query("timestamp") timestamp: String?, // Optional parameter
        @Query("timespan") timespan: String,
        @Query("adjusted") adjusted: Boolean,
        @Query("window") window: Int,
        @Query("series_type") series_type: String,
        @Query("order") order: String,
        @Query("apiKey") apiKey: String
    ): SimpleMovingAverage

    @GET("/v1/indicators/ema/{stockTicker}")
    suspend fun getEMA(
        @Path("stockTicker") stockTicker: String,
        @Query("timespan") timespan: String,
        @Query("adjusted") adjusted: Boolean,
        @Query("window") window: Int,
        @Query("series_type") seriesType: String,
        @Query("order") order: String,
        @Query("apiKey") apiKey: String

        ): ExponintialMovingAverage

    @GET("/v1/indicators/macd/{stockTicker}")
    suspend fun getMACD(
        @Path("stockTicker") stockTicker: String,
        @Query("timespan") timespan: String,
        @Query("adjusted") adjusted: Boolean,
        @Query("short_window") shortWindow: Int,
        @Query("long_window") longWindow: Int,
        @Query("signal_window") signalWindow: Int,
        @Query("series_type") seriesType: String,
        @Query("order") order: String,
        @Query("apiKey") apiKey: String

        ): MovingAverageDivergence

    @GET("/v1/indicators/rsi/{stockTicker}")
    suspend fun getRSI(
        @Path("stockTicker") stockTicker: String,
        @Query("timespan") timespan: String,
        @Query("adjusted") adjusted: Boolean,
        @Query("window") window: Int,
        @Query("series_type") seriesType: String,
        @Query("order") order: String,
        @Query("apiKey") apiKey: String
        ): RelativeStengthIndex

}