package com.example.stocki.market.Stocks

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.NavController

@Composable
fun MarketsScreen(viewModel: MarketViewModel = hiltViewModel(), onTickerClicked: (ticker :String) -> Unit) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData("2024-03-01") // Fetch data automatically when the screen is displayed
        Log.d("StockiMain", "getGroupedDailyBars ${viewModel.fetchData("2024-03-01")}")

    }

    when (val marketState = state) {
        is MarketState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is MarketState.Data -> {
            val dataList = marketState.data ?: emptyList()
            LazyColumn {
                items(dataList.size) { index ->
                    val aggregateData = dataList[index]
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                onTickerClicked(aggregateData.T)
                                Log.d("StockiMarket", "onTickerClicked ${aggregateData.T}")
                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Company: ${aggregateData.T}")
                            Text("Opening Price: ${aggregateData.o}")
                            Text("Closing Price: ${aggregateData.c}")
                            Text("High Price: ${aggregateData.h}")
                            Text("Low Price: ${aggregateData.l}")
                            Text("Volume: ${aggregateData.v}")
                            Text("Volume Weighted Average Price: ${aggregateData.vw}")
                            Text("Timestamp: ${aggregateData.t}")
                            Text("Number of items in the bar: ${aggregateData.n}")
                        }
                    }
                }
            }
        }
        is MarketState.Error -> {
            Text(marketState.error, modifier = Modifier.padding(16.dp))
        }
    }


}

