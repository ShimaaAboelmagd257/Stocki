package com.example.stocki.market.stocks

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter

@Composable
fun MarketsScreen(viewModel: MarketViewModel = hiltViewModel(), onTickerClicked: (ticker :String) -> Unit) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData("2024-04-05") // Fetch data automatically when the screen is displayed
        Log.d("StockiMain", "getGroupedDailyBars ${viewModel.fetchData("2024-04-05")}")
      //  viewModel.fetchTickerLogo()

    }
    LaunchedEffect(Unit) {
        // Fetch ticker logos automatically when the screen is displayed
        val dataList = (state as? MarketState.Data)?.data ?: emptyList()
        val tickers = dataList.map { it.T }
        viewModel.fetchTickerLogo(tickers)
    }

    when (val marketState = state) {

        is MarketState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is MarketState.Data -> {
            val dataList = marketState.data ?: emptyList()
            val logoMap = (state as? MarketState.Logo)?.logo // Retrieve logo map if available
            LazyColumn {
                items(dataList.size) { index ->
                    val aggregateData = dataList[index]
                    val ticker = aggregateData.T
                    val logoUrl = logoMap?.get(ticker)
                    Log.d("StockiMarket", "logoUrl ${logoUrl}")


                    Card(
                        modifier = Modifier.border(5.dp, Color.Black)
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clickable {
                                onTickerClicked(aggregateData.T)
                                Log.d("StockiMarket", "onTickerClicked ${aggregateData.T}")
                            }
                    ) {
                        Column(
                            //mor.paddidifier = Modifieng(16.dp)
                        ) {
                            logoUrl?.let { url ->
                                Image(
                                    painter = rememberImagePainter(url),
                                    contentDescription = "Logo",
                                    modifier = Modifier.size(64.dp)
                                )
                            }
                            Text(" ${aggregateData.T}", fontWeight = FontWeight.Bold)
                            Text("Opening Price: " + aggregateData.o)
                            Text("Closing Price: ${aggregateData.c}")
                            Text("High Price: ${aggregateData.h}")
                            Text("Low Price: ${aggregateData.l}", color = Color.Red)
                          /*  Text("Volume: ${aggregateData.v}")
                           *//* LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                progress = aggregateData.v / maxVolume // Assuming maxVolume is the maximum possible volume
                            )*//*
                            Text("Volume Weighted Average Price: ${aggregateData.vw}")
                            Text("Timestamp: ${aggregateData.t}")
                            Text("Number of items in the bar: ${aggregateData.n}")*/
                        }
                    }
                }
            }
        }
        is MarketState.Error -> {
            Text(marketState.error, modifier = Modifier.padding(16.dp))
            Log.d("StockitickerInfoState", "marketState  ${marketState.error}")

        }
        is MarketState.Logo -> {

            }

        }
    }




