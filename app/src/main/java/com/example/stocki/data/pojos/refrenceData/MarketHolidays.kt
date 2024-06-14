package com.example.stocki.data.pojos

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "MarketHoliday")
@Parcelize
data class MarketHoliday(
    @PrimaryKey(autoGenerate = true)
    val date: String,
    val exchange: String,
    val name: String,
    val status: String,
    val open: String? = null,
    val close: String? = null
) : Parcelable