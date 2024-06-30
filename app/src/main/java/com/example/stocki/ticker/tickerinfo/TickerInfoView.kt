package com.example.stocki.ticker.tickerinfo

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stocki.TabBarItem
import com.example.stocki.utility.Constans
import com.example.stocki.watchlists.WatchListViewModel

@Composable
fun tickerInfoView(ticker: String,viewModel: TickerInfoViewModel = hiltViewModel(),watchListViewModel: WatchListViewModel = hiltViewModel(), onFetchData: (String) -> Unit) {

    val state by viewModel.state.collectAsState()
   // val watchListState by watchListViewModel.watchListState.collectAsState()

    LaunchedEffect(ticker) {
        onFetchData(ticker)
        Log.d("StockitickerInfoView", "onFetchData ${ticker}")
    }
    when (val tickerInfoState = state) {
        is TickerInfoState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(150.dp , 300.dp))
        }
        is TickerInfoState.Data -> {

            val companies = tickerInfoState.data
            val scrollState = rememberScrollState()

            Column(modifier = Modifier
                .padding(16.dp)
                .verticalScroll(scrollState),
                   verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                companies?.forEach { company ->
                        Constans.LoadNetworkSvgImage(
                            url = "${company.branding.logo_url}?apiKey=${Constans.Api_Key}"  ,
                             modifier = Modifier
                                 .fillMaxWidth()
                                 .height(200.dp)
                                 .clip(RoundedCornerShape(8.dp))
                        )
                    Button(onClick = {  } ) {
                        Text(text = "Buy")
                    }
                    Button(onClick = {  }) {
                        Text(text = "Sell")
                    }

                  /*  IconButton(onClick = {
                        watchListViewModel.insertData()
                    }
                    ){

                    }*/
                    //
                    Text(text = "${company.name}" , fontWeight = FontWeight.Bold)
                    Text(text = "Ticker: ${company.ticker}")

                    Text(text = "Active: ${company.active}")
                    Text(text = "Address: " +
                            "${company.address?.address1}, ${company.address?.city}, ${company.address?.state}, ${company.address?.postal_code}")
                    Text(text = "Currency Name: ${company.currency_name}")
                    Text(text = "Primary Exchange: ${company.primary_exchange}")
                    Text(text = "List Date: ${company.list_date ?: "Not available"} ")
                    Text(text = "Locale: ${company.locale}")
                    Text(text = "Market: ${company.market}")
                    Text(text = "Market Cap: ${company.market_cap}")
                    Text(text = "Phone Number: ${company.phone_number}")
                    Text(text = "Round Lot: ${company.round_lot}")
                    Text(text = "SIC Code: ${company.sic_code ?: "Not available" }")
                    Text(text = "SIC Description: ${company.sic_description ?: "Not available"}")
                    Text(text = "Ticker Root: ${company.ticker_root ?: "Not available" }")
                    Text(text = "Total Employees: ${company.total_employees}")
                    Text(text = "Type: ${company.type}")
                    Text(text = "Weighted Shares Outstanding: ${company.weighted_shares_outstanding}")
                    Text(text = "Description: " + "${company.description}")
                    Constans.ClickableUrl(url = company.homepage_url)
                    Spacer(modifier = Modifier.height(8.dp)) // Add spacing between companies
                }
            }
            Log.d("StockitickerInfoState", "tickerInfoStateData  ${tickerInfoState.data}")

        }

        is TickerInfoState.Error -> {
            Log.d("StockitickerInfoState", "tickerInfoState  ${tickerInfoState.error}")
            Text(tickerInfoState.error, modifier = Modifier.padding(150.dp , 300.dp))

        }


        else -> {}
    }
}