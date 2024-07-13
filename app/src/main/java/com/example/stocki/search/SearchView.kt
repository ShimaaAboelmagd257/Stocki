package com.example.stocki.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import com.example.stocki.data.pojos.TickerTypes
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun search(viewmodel: SearchViewmodel = hiltViewModel() ,  onSearchRequested: () -> Unit ){

    val state by viewmodel.state.collectAsState()
    val titleList = listOf("stocks","crypto","otc","fx","indices")

    LaunchedEffect(Unit) {
        viewmodel.fetchData(titleList)
        Log.d("StockiSearch", "LaunchedEffect ")

    }

    var selectedTab by remember { mutableStateOf("otc") }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val selectedTabIndex = when (selectedTab) {
        "stocks" -> 0
        "crypto" -> 1
        "fx" -> 2
        "otc" -> 3
        "indices" -> 4
        else -> 0
    }
    Column(
        modifier = Modifier.fillMaxSize()

    ){
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            TabRow(
                selectedTabIndex =// pagerState.currentPage
                when {
                 //   isSearching -> -1 // No tab selected when searching
                    else -> when (selectedTab) {
                        "stocks" -> 0
                        "crypto" -> 1
                        "fx" -> 2
                        "otc" -> 3
                        "indices" -> 4
                        else -> 0
                    }
                },
                contentColor = Color.Black
            ) {
                titleList.forEachIndexed { index, text ->
                    Tab(
                        selected =pagerState.currentPage == index,
                        onClick = {
                        scope.launch{
                            pagerState.scrollToPage(index)
                        }
                            selectedTab = titleList[index]
                         },
                        text = { Text(text = text,modifier = Modifier
                            .fillMaxWidth()) }
                    )
                }


            }
        }
    }


    when (val searchState = state) {
        is SearchState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is SearchState.Data -> {
            val filteredData = searchState.data?.filter {
                it.market.equals(selectedTab, ignoreCase = true)


            }
            if (filteredData.isNullOrEmpty()) {
                Text("No data available", modifier = Modifier.padding(16.dp))
            } else {
                TickerListScreen(tickers = filteredData , onSearchRequested = onSearchRequested )
            }

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
fun TickerListScreen(tickers: List<TickerTypes> ,  onSearchRequested: () -> Unit) {
    val AppBarHeight = 56.dp
    Log.d("StockiSearch", "filteredData ${tickers.size} ")

    Box(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(150.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppBarHeight)
        ){
            items(tickers.size) { index ->
                val ticker = tickers[index]
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clickable {

                        }
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text("name: ${ticker.name}")
                        Text("ticker: ${ticker.ticker}")
                        Text("locale: ${ticker.locale}")
                    }
                }
            }
        }
        IconButton(
            onClick = { onSearchRequested() },
            modifier = Modifier
                .background(Color.Black, shape = CircleShape)
                .align(Alignment.BottomEnd)
                .padding(70.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color.Blue,
                modifier = Modifier.size(100.dp)
            )
        }

    }
}
@Preview
@Composable
fun PreviewTickerListScreen() {
    val sampleTickersList = listOf(
        TickerTypes(1, "Market 1", "Name 1", "Ticker 1", "Type 1", "Locale 1"),
        TickerTypes(2, "Market 2", "Name 2", "Ticker 2", "Type 2", "Locale 2"),
        TickerTypes(3, "Market 3", "Name 3", "Ticker 3", "Type 3", "Locale 3")
    )
    TickerListScreen(tickers = sampleTickersList, onSearchRequested = {})
}
