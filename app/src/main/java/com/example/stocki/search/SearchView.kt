package com.example.stocki.search

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.stocki.data.pojos.Ticker
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.*
import com.example.stocki.data.pojos.TickerTypes

@Composable
fun search(navController: NavHostController,viewmodel: SearchViewmodel = hiltViewModel()){

    val state by viewmodel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewmodel.fetchData(listOf("stocks","crypto","otc","fx","indices"))
        /*viewmodel.fetchData("crypto")
        viewmodel.fetchData("fx")
       viewmodel.fetchData("otc")
        viewmodel.fetchData("indices")*/
        Log.d("StockiSearch", "LaunchedEffect ")

    }


    var selectedTab by remember { mutableStateOf("otc") }
    Column(
        modifier = Modifier.fillMaxSize()

    ){
    TopAppBar(
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        TabRow(
            selectedTabIndex = when (selectedTab) {
                "stocks" -> 0
                "crypto" -> 1
                "fx" -> 2
                "otc" -> 3
                "indices" -> 4
                else -> 0
            },
            contentColor = Color.Black
        ) {
            // Loop through the list of market types and create a tab for each
            listOf("Stocks", "Crypto", "FX", "OTC", "Indices").forEachIndexed { index, text ->
                Tab(
                    selected = selectedTab == text.toLowerCase(),
                    onClick = { selectedTab = text.toLowerCase() },
                    text = { Text(text) }
                )
            }
        }
    }
   }


  //  Spacer(modifier = Modifier.height(56.dp))
    when (val searchState = state) {
        is SearchState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
       /* is SearchState.Data -> {

            val filteredData = searchState.data?.filter { it.market == selectedTab }

            TickerListScreen(tickers = filteredData ?: emptyList())
            Log.d("StockiSearch", "filteredData ${filteredData?.size} ${selectedTab}")

        }*/
        is SearchState.Data -> {
            val filteredData = searchState.data?.filter { it.market.equals(selectedTab, ignoreCase = true) }

            if (filteredData.isNullOrEmpty()) {
                // No data available for the selected market type
                Text("No data available", modifier = Modifier.padding(16.dp))
            } else {
                TickerListScreen(tickers = filteredData)
            }
            Log.d("StockiSearch", "filteredData ${filteredData?.size} ${selectedTab}")

        }



        is SearchState.Error -> {
            Text(
                text = "Error: ${searchState.error}",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
              Log.d("StockiSearch", "SearchState.Error ${searchState.error}")

        }



    }


}

@Composable
fun TickerListScreen(tickers: List<TickerTypes>) {
    val AppBarHeight = 56.dp
    Log.d("StockiSearch", "filteredData ${tickers.size} ")

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = AppBarHeight)
    ){
                items(tickers.size) { index ->
                    val tickers = tickers[index]
                    //val tickers = searchState.data[index]
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {

                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("name: ${tickers.name}")
                            Text("ticker: ${tickers.ticker}")
                            Text("locale: ${tickers.locale}")
                          //  Text("currencyName: ${tickers.currencyName ?: "NOT FOUND"}")

                        }
                    }
                }
            }}