package com.example.stocki.data.pojos.refrenceData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Exchange(
    val count: Int,
    val requestId: String,
    val results: List<ConditionResult>,
    val status: String
) : Parcelable

@Parcelize
data class ConditionResult(
    val acronym: String,
    val assetClass: String,
    val id: Int,
    val locale: String,
    val mic: String,
    val name: String,
    val operatingMic: String,
    val participantId: String,
    val type: String,
    val url: String
) : Parcelable
