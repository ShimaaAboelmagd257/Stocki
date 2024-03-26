package com.example.stocki.data.localDatabase

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.stocki.data.pojos.TickerTypes

@Database(entities = [TickerTypes::class], version = 3)
abstract class StockiDatabase : RoomDatabase() {

    abstract fun TickerDAO() : TickerDAO

    companion object {
        @Volatile
        private var INSTANCE: StockiDatabase? = null
        fun getInstance(context: Context): StockiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, StockiDatabase::class.java, "Stocki Database"
                ).fallbackToDestructiveMigration()
                    .build()
                Log.d("StockiDataBase", "singleton instance apply succeeded")
                INSTANCE = instance
                instance
            }
        }
    }
}
