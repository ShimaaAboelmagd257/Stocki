package com.example.stocki.search

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Tab
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import com.example.stocki.data.pojos.TickerTypes
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@Composable
fun search(viewmodel: SearchViewmodel = hiltViewModel() ,  onSearchRequested: () -> Unit ){

    val state by viewmodel.state.collectAsState()
    val titleList = listOf("stocks","crypto","otc","fx","indices")

    LaunchedEffect(Unit) {
        viewmodel.fetchData(titleList)
        Log.d("StockiSearch", "LaunchedEffect ")
    }

    //var selectedTab by remember { mutableStateOf("otc") }

    when (val searchState = state) {
        is SearchState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is SearchState.Data -> {
                TickerListScreen(
                    tickers = searchState.data?: emptyList() ,
                    titleList = titleList,
                    onSearchRequested = onSearchRequested
                )
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TickerListScreen(
    tickers: List<TickerTypes> ,
    titleList: List<String>,
    onSearchRequested: () -> Unit) {
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    val AppBarHeight = 56.dp
    Log.d("StockiSearch", "filteredData ${tickers.size} ")
    Column(
        modifier = Modifier.fillMaxSize()

    ) {
        TopAppBar(
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.background(androidx.compose.material3.MaterialTheme.colorScheme.surface),
                backgroundColor = Color.Transparent
            ) {
                titleList.forEachIndexed { index, text ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                            //selectedTab = titleList[index]
                        },
                        text = {
                            Text(
                                text = text, modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    )
                }

            }
        }

            HorizontalPager(state = pagerState, count = titleList.size) { page ->
                val filteredTickers = tickers.filter {
                    it.market.equals(titleList[page], ignoreCase = true)
                }

                if (filteredTickers.isNullOrEmpty()) {
                    Text("No data available", modifier = Modifier.padding(16.dp))
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(filteredTickers) { ticker ->
                            TickerCard(ticker = ticker)
                        }
                    }
                }
            }

        Box(modifier = Modifier.fillMaxSize()) {
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
}
@Composable
fun TickerCard(ticker: TickerTypes) {
    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clickable { }
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text("Name: ${ticker.name}")
            Text("Ticker: ${ticker.ticker}")
            Text("Locale: ${ticker.locale}")
        }
    }
}

/*@Preview
@Composable
fun PreviewTickerListScreen() {
    val sampleTickersList = listOf(
        TickerTypes(1, "Market 1", "Name 1", "Ticker 1", "Type 1", "Locale 1"),
        TickerTypes(2, "Market 2", "Name 2", "Ticker 2", "Type 2", "Locale 2"),
        TickerTypes(3, "Market 3", "Name 3", "Ticker 3", "Type 3", "Locale 3")
    )
    TickerListScreen(tickers = sampleTickersList, onSearchRequested = {})
}*/
