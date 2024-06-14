package com.example.stocki.data.localDatabase

import android.content.Context
import com.example.stocki.data.pojos.*
import javax.inject.Inject

class ConcreteLocalSource @Inject constructor(context: Context):localSource {

    private val stockiDatabase:StockiDatabase = StockiDatabase.getInstance(context)
    private val watchListDao:WatchListDao by lazy {
        stockiDatabase.WatchListDao()
    }
    private val tickerDAO:TickerDAO by lazy { stockiDatabase.TickerDAO() }
    private val tickerLogoDAO: TickerLogoDAO by lazy {
        stockiDatabase.TickerLogoDAO()
    }
    private val newsLocalDAO:newsLocalDAO by lazy {
        stockiDatabase.newsLocalDao()
    }
   /* private val tickerInfoDAO:TickerInfoDAO by lazy {
        stockiDatabase.TickerInfoDAO()
    }*/
    companion object {
        @Volatile
        private var INSTANCE : ConcreteLocalSource?=null

        fun getInstance(context: Context): ConcreteLocalSource {
            return INSTANCE?: synchronized(this){
                val instance = ConcreteLocalSource(context)
                INSTANCE=instance
                instance
            }
        }

    }

   override fun getgetAllTickerTypes(/*market: String*/): List<TickerTypes>{
        return tickerDAO.getAllTickerTypes(/*market*/)
    }

    override  suspend fun insertTypes(tickerTypes: List<TickerTypes>){
        tickerDAO.insertTypes(tickerTypes)
    }

    override fun getAllWatchLists(): List<TickerTypes> {
        return watchListDao.getAllWatchLists()
    }

    override suspend fun insertTicker(types: List<TickerTypes>) {
         watchListDao.insertTicker(types)
    }

    override fun deleteTicker(ticker: TickerTypes) {
        watchListDao.deleteTicker(ticker)
    }

    /* override fun getAllTickerInfo(): List<Company> {
       return  tickerInfoDAO.getAllTickerInfo()
     }

     override suspend fun insertTickerInfo(company: List<Company>) {
       tickerInfoDAO.insertTickerInfo(company)    }*/
    override fun getAllTickerLogo(): List<BrandingSaved> {
       return tickerLogoDAO.getAllTickerLogo()
    }


    override suspend fun insertTickerLogo(brandingSaved: List<BrandingSaved>) {
        tickerLogoDAO.insertTickerLogo(brandingSaved)
    }

    override fun getTickerLogo(ticker: String): BrandingSaved {
     return   tickerLogoDAO.getTickerLogo(ticker)
    }

    override suspend fun insertNews(newsLocal: List<NewsItem>) {
        newsLocalDAO.insertNewsLocal(newsLocal)
    }

    override fun getNews(): List<NewsItem> {
        return   newsLocalDAO.getAllNewsLocal()
    }

    override fun getNewsItemById(id: String): NewsItem {
        return  newsLocalDAO.getNewsItemById(id)
    }

}