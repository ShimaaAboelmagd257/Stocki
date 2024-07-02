package com.example.stocki.account.profile

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@SuppressLint("SuspiciousIndentation")
@Composable
fun UserPortfolio( viewmodel: ProfileViewmodel = hiltViewModel() , userId:String) {
   val portfolioState by viewmodel.portfolioState.collectAsState()

    LaunchedEffect(userId ){
        viewmodel.fetchUserPortfolio(userId)
    }

    when(val portfolioState = portfolioState){
        is PortfolioState.Data -> {
            val portfolio = portfolioState.protfolio
            portfolio.forEach { portfolio ->
                Column() {
                    Text(text = "Ticker" + portfolio.ticker)
                    Text(text = "Average price" + portfolio.averagePrice)
                    Text(text = "Quantity " + portfolio.averagePrice)
                }
            }

        }
        is PortfolioState.Error -> {
            Text(portfolioState.error, modifier = Modifier.padding(16.dp))
            Log.d("StockitickerInfoState", "marketState  ${portfolioState.error}")
        }
        PortfolioState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
    }
}