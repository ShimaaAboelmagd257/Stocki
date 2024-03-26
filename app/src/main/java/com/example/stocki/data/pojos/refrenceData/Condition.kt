package com.example.stocki.data.pojos.refrenceData

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConditionResponse(
    val count: Int,
    val requestId: String,
    val results: List<Result>,
    val status: String
) : Parcelable

@Parcelize
data class Result(
    val assetClass: String,
    val dataTypes: List<String>,
    val id: Int,
    val name: String,
    val sipMapping: Map<String, String>,
    val type: String,
    val updateRules: UpdateRules
) : Parcelable

@Parcelize
data class UpdateRules(
    val consolidated: UpdateRule,
    val marketCenter: UpdateRule
) : Parcelable

@Parcelize
data class UpdateRule(
    val updatesHighLow: Boolean,
    val updatesOpenClose: Boolean,
    val updatesVolume: Boolean
) : Parcelable
