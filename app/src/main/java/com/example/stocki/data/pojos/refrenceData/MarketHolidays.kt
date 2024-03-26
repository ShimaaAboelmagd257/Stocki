package com.example.stocki.data.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class MarketHoliday(
    val date: String,
    val exchange: String,
    val name: String,
    val status: String,
    val open: String? = null,
    val close: String? = null
) : Parcelable