package com.example.stocki.ticker.tickerinfo

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
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
fun tickerInfoView(ticker: String,viewModel: TickerInfoViewModel = hiltViewModel(), onFetchData: (String) -> Unit) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(ticker) {
        onFetchData(ticker)
        Log.d("StockitickerInfoView", "onFetchData ${ticker}")

    }
    when (val tickerInfoState = state) {
        is TickerInfoState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is TickerInfoState.Data -> {

            val companies = tickerInfoState.data
            Column(modifier = Modifier.padding(16.dp)) {
                companies.forEach { company ->
                    Text(text = "Company Name: ${company.name}")
                    Text(text = "Ticker: ${company.ticker}")
                    Text(text = "Description: ${company.description}")
                    Text(text = "Active: ${company.active}")
                    Text(text = "Address: ${company.address.address1}, ${company.address.city}, ${company.address.state}, ${company.address.postalCode}")
                    Text(text = "Currency Name: ${company.currencyName}")
                    Text(text = "List Date: ${company.listDate ?: "Not available"} ")
                    Text(text = "Locale: ${company.locale}")
                    Text(text = "Market: ${company.market}")
                    Text(text = "Market Cap: ${company.marketCap}")
                    Text(text = "Phone Number: ${company.phoneNumber}")
                    Text(text = "Round Lot: ${company.roundLot}")
                    Text(text = "SIC Code: ${company.sicCode ?: "Not available" }")
                    Text(text = "SIC Description: ${company.sicDescription ?: "Not available"}")
                    Text(text = "Ticker Root: ${company.tickerRoot ?: "Not available" }")
                    Text(text = "Total Employees: ${company.totalEmployees}")
                    Text(text = "Type: ${company.type}")
                    Text(text = "Weighted Shares Outstanding: ${company.weightedSharesOutstanding}")
                    Text(text = "Homepage URL: ${company.homepageUrl ?: "Not available"}")
                    Text(text = "Primary Exchange: ${company.primaryExchange}")
                    Spacer(modifier = Modifier.height(8.dp)) // Add spacing between companies
                }
            }

        }

        is TickerInfoState.Error -> {}


        else -> {}
    }
   /* LaunchedEffect(ticker) {
        viewModel.fetchData(ticker)
        Log.d("StockiMarket", "onTickerClicked ${ticker}")

    }*/
}