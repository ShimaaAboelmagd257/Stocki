package com.example.stocki.data.localDatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stocki.data.pojos.TickerTypes


@Dao
interface TickerDAO {

    @Query("SELECT * FROM Tickertypes")
    fun getAllTickerTypes(/*market: String*/): List<TickerTypes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTypes(types: List<TickerTypes>)
}