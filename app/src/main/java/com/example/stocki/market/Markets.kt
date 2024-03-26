package com.example.stocki.market

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
import com.example.stocki.NavigationRoute
import com.example.stocki.utility.Constans

@Composable
fun MarketsScreen(viewModel: MarketViewModel = hiltViewModel() ,   navController: NavController , onTickerClicked: (ticker :String) -> Unit) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData("2024-03-01") // Fetch data automatically when the screen is displayed
        Log.d("StockiMain", "getGroupedDailyBars ${viewModel.fetchData("2024-03-01")}")

    }

    when (val marketState = state) {
        is MarketState.Loading -> {
            // Display loading indicator
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is MarketState.Data -> {
            val dataList = marketState.data ?: emptyList()
            LazyColumn {
                items(dataList.size) { index ->
                    val aggregateData = dataList[index]
                    //val aggregateData = marketState.data[index]
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {
                                // Navigate to TickerInfoScreen when card is clicked
                              //  navController.navigate("${NavigationRoute.TICKER_INFO.route}/${aggregateData.t}")
                                //
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
            // Display error message
            Text(marketState.error, modifier = Modifier.padding(16.dp))
        }
    }

    /*Button(onClick = { viewModel.fetchData("") }, modifier = Modifier.padding(16.dp)) {
        Text("Fetch Data")
    }*/
}

