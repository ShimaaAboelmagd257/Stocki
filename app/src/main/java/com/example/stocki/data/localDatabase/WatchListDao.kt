package com.example.stocki.data.localDatabase

import androidx.room.*
import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.TickerTypes
import com.example.stocki.data.pojos.WatchList


@Dao
interface WatchListDao {
    @Query("SELECT * FROM WatchList")
    suspend fun getAllWatchLists(): List<WatchList>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicker(types: WatchList)

    @Query("SELECT * FROM WatchList WHERE T = :T")
   suspend fun getTickerById(T:String) : WatchList
    @Delete
   suspend fun deleteTicker(ticker : WatchList)
}

