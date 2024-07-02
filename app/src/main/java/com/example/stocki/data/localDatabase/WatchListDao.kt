package com.example.stocki.data.localDatabase

import androidx.room.*
import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.TickerTypes


@Dao
interface WatchListDao {

    @Query("SELECT * FROM AggregateData")
    fun getAllWatchLists(): List<AggregateData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicker(types: AggregateData)

    @Query("SELECT * FROM AggregateData WHERE T = :T")
    fun getTickerById(T:String) : AggregateData
    @Delete
    fun deleteTicker(ticker : AggregateData)
}

