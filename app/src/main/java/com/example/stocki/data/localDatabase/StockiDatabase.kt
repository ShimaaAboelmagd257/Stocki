package com.example.stocki.data.localDatabase

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.stocki.data.pojos.*

@Database(entities = [/*TickerTypes::class*/  /*BrandingSaved::class ,*/ NewsItem::class , AggregateData::class, WatchList::class ], version = 25)
abstract class StockiDatabase : RoomDatabase() {

   // abstract fun TickerDAO() : TickerDAO
  //  abstract fun TickerLogoDAO() :TickerLogoDAO
    abstract fun WatchListDao() : WatchListDao
    abstract fun newsLocalDao():newsLocalDAO
    abstract fun marketDao():marketDAO
    companion object {
        @Volatile
        private var INSTANCE: StockiDatabase? = null
        fun getInstance(context: Context): StockiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, StockiDatabase::class.java, "Stocki Database"
                )
                    .addMigrations(MIGRATION_22_23)
                    .build()
                Log.d("StockiDataBase", "singleton instance apply succeeded")
                INSTANCE = instance
                instance
            }
        }
        private val MIGRATION_22_23 = object : Migration(24, 25) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS WatchList")

                // Recreate the table with the correct schema
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS `WatchList` (
                        `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                        `c` REAL NOT NULL,
                        `T` TEXT NOT NULL,
                        `h` REAL NOT NULL,
                        `absoluteChangeFormatted` REAL NOT NULL,
                        `percentageChangeFormatted` REAL NOT NULL,
                        `l` REAL NOT NULL
                    )
                """.trimIndent())
                database.execSQL("DROP TABLE IF EXISTS TickerTypes")
                database.execSQL("DROP TABLE IF EXISTS BrandingSaved")

            }
        }
    }
}
