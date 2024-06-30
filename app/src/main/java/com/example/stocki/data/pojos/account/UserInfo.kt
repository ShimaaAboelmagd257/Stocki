package com.example.stocki.data.pojos.account

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserInfo(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val balance :Double = 100.00
) : Parcelable