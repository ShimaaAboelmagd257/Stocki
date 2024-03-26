package com.example.stocki.data.localDatabase

import android.content.Context
import com.example.stocki.data.pojos.TickerTypes
import javax.inject.Inject

class ConcreteLocalSource @Inject constructor(context: Context):localSource {

    private val stockiDatabase:StockiDatabase = StockiDatabase.getInstance(context)
    private val tickerDAO:TickerDAO by lazy { stockiDatabase.TickerDAO() }
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

}