package com.example.stocki.data.localDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stocki.data.pojos.*


@Dao
interface TickerDAO {

    @Query("SELECT * FROM Tickertypes")
    fun getAllTickerTypes(/*market: String*/): List<TickerTypes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypes(types: List<TickerTypes>)
}
@Dao
interface newsLocalDAO {

    @Query("SELECT * FROM newsLocal")
    fun getAllNewsLocal(): List<NewsItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNewsLocal(types: List<NewsItem>)

    @Query("SELECT * FROM newsLocal WHERE id = :id")
    fun getNewsItemById(id:String) : NewsItem
}


/*
@Dao
interface TickerInfoDAO {

    @Query("SELECT * FROM BrandingSaved")
    fun getAllTickerInfo(): List<Company>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTickerInfo(types: List<Company>)
}
*/

@Dao
interface TickerLogoDAO {

    @Query("SELECT * FROM BrandingSaved")
    fun getAllTickerLogo(): List<BrandingSaved>

    @Query("SELECT * From BrandingSaved where ticker =:ticker")
    fun getTickerLogo(ticker:String) :BrandingSaved

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTickerLogo(types: List<BrandingSaved>)
}