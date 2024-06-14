package com.example.stocki.data.pojos

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyResponse(
    val request_id: String,
    val results: Company,
    val status: String
): Parcelable

//@Entity(tableName = "TickerInfo")
@Parcelize
data class Company (
   // @PrimaryKey(autoGenerate = true)
    val ticker: String,
    val name: String,
    val market: String,
    val locale: String,
    val primary_exchange: String,
    val type: String,
    val active: Boolean,
    val currency_name: String,
    val cik: String,
    val composite_figi: String,
    val share_class_figi: String,
    val market_cap: Double,
    val phone_number: String,
    val address: address,
    val description: String,
    val sic_code: String,
    val sic_description: String,
    val ticker_root: String,
    val homepage_url: String,
    val total_employees: Int,
    val list_date: String,
    val branding: branding,
    val share_class_shares_outstanding: Long,
    val weighted_shares_outstanding: Long,
    val round_lot: Int
): Parcelable

@Parcelize
data class address(
    val address1: String? =" Adress not FOUND",
    val address2: String?,
    val city: String,
    val state: String,
    val postal_code: String,
): Parcelable


@Parcelize
data class branding(
    val logo_url: String? = "empty",
    val icon_url: String? = "empty"
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