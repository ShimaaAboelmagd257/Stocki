package com.example.stocki.market.Dailyoc

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

@Composable
fun DailyOC (ticker: String,date:String,viewModel: DailyViewmodel = hiltViewModel(),  onFetchData: (String , String) -> Unit) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(ticker , date) {
        onFetchData(ticker, date)
    }

    when (val dailyState = state) {
        is DailyState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is DailyState.Data -> {
            val tickers = dailyState.data
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Company: ${ticker}")
                Text("Symbole: ${tickers.symbol}")
                Text("Opening Price: ${tickers.open}")
                Text("Closing Price: ${tickers.close}")
                Text("High Price: ${tickers.high}")
                Text("Low Price: ${tickers.low}")
                Text("Volume: ${tickers.volume}")
                Text("Volume Weighted Average Price: ${tickers.volume}")
                Text("After Hours: ${tickers.afterHours}")
            }
        }
        is DailyState.Error -> {
            // Display error message
            Text(dailyState.error, modifier = Modifier.padding(16.dp))
        }
    }
}