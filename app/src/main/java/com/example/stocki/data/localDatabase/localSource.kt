package com.example.stocki.data.localDatabase

import com.example.stocki.data.pojos.BrandingSaved
import com.example.stocki.data.pojos.Company
import com.example.stocki.data.pojos.TickerTypes

interface localSource {


    fun getgetAllTickerTypes(): List<TickerTypes>
    suspend fun insertTypes(tickerTypes: List<TickerTypes>)

/*
    fun getAllTickerInfo(): List<Company>
    suspend fun insertTickerInfo(brandingSaved: List<Company>)
*/

    fun getAllTickerLogo(): List<BrandingSaved>
    suspend fun insertTickerLogo(brandingSaved: List<BrandingSaved>)
    fun getTickerLogo(ticker:String): BrandingSaved

}