package com.example.stocki.data.pojos

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyResponse(
    val requestId: String,
    val results: Company,
    val status: String
): Parcelable

//@Entity(tableName = "TickerInfo")
@Parcelize
data class Company (
   // @PrimaryKey(autoGenerate = true)
    val id: Long = 0 ,
    val active: Boolean,
    val address: address?,
    val branding: branding?,
    val cik: String,
    val composite_figi: String,
    val currency_name: String,
    val delisted_utc:String,
    val description: String,
    val homepage_url: String?,
    val list_date: String,
    val locale: String,
    val market: String,
    val market_cap: Double,
    val name: String,
    val phone_number: String,
    val primary_exchange: String,
    val round_lot: Int,
    val share_class_figi: String,
    val share_class_shares_outstanding: Long,
    val sic_code: String,
    val sic_description: String,
    val ticker: String,
    val ticker_root: String,
    val ticker_suffix:String,
    val total_employees: Int,
    val type: String,
    val weighted_shares_outstanding: Long
): Parcelable

@Parcelize
data class address(
    val address1: String? =" Adress not FOUND",
    val address2: String?,
    val city: String,
    val postal_code: String,
    val state: String
): Parcelable


@Parcelize
data class branding(
    val icon_url: String?,
    val logo_url: String?
): Parcelable

@Entity(tableName = "BrandingSaved")
@Parcelize
data class BrandingSaved(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0 ,
    val ticker:String,
    val iconUrl: String?,
    val logoUrl: String?
    ): Parcelable