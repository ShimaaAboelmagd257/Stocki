package com.example.stocki.data.pojos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyResponse(
    val requestId: String,
    val results: Company,
    val status: String
): Parcelable


@Parcelize
data class Company (
    val active: Boolean,
    val address: address,
    val branding: branding,
    val cik: String,
    val compositeFigi: String,
    val currencyName: String,
    val description: String,
    val homepageUrl: String?,
    val listDate: String,
    val locale: String,
    val market: String,
    val marketCap: Long,
    val name: String,
    val phoneNumber: String,
    val primaryExchange: String,
    val roundLot: Int,
    val shareClassFigi: String,
    val shareClassSharesOutstanding: Long,
    val sicCode: String,
    val sicDescription: String,
    val ticker: String,
    val tickerRoot: String,
    val totalEmployees: Int,
    val type: String,
    val weightedSharesOutstanding: Long
): Parcelable

@Parcelize
data class address(
    val address1: String,
    val city: String,
    val postalCode: String,
    val state: String
): Parcelable


@Parcelize
data class branding(
    val iconUrl: String,
    val logoUrl: String
): Parcelable
