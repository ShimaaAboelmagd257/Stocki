package com.example.stocki.data.localDatabase

import androidx.room.*
import com.example.stocki.data.pojos.TickerTypes


@Dao
interface WatchListDao {

    @Query("SELECT * FROM Tickertypes")
    fun getAllWatchLists(): List<TickerTypes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTicker(types: List<TickerTypes>)

    @Delete
    fun deleteTicker(ticker : TickerTypes)
}

