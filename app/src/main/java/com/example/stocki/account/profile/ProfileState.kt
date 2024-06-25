package com.example.stocki.account.profile

import com.example.stocki.data.pojos.account.PortfolioItem
import com.example.stocki.data.pojos.account.UserInfo


sealed class ProfileState {
    object Loading : ProfileState()
    data class  Data(val user: UserInfo): ProfileState()
    data class Error(val error: String) : ProfileState()
}

sealed class PortfolioState {
    object Loading : PortfolioState()
    data class  Data(val protfolio: List<PortfolioItem>): PortfolioState()
    data class Error(val error: String) : PortfolioState()
}