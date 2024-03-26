package com.example.stocki.data.pojos.refrenceData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class DividendsResponse(
    val next_url: String?,
    val results: List<Dividend>,
    val status: String
) : Parcelable


@Parcelize
data class Dividend(
    val cash_amount: Double,
    val declaration_date: String,
    val dividend_type: String,
    val ex_dividend_date: String,
    val frequency: Int,
    val pay_date: String,
    val record_date: String,
    val ticker: String
) : Parcelable