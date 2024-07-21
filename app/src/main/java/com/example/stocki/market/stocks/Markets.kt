package com.example.stocki.market.stocks
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stocki.R
import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.utility.Constans.currentAdujestedTime
import com.example.stocki.utility.Constans.timestampToDate
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState

import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MarketsScreen(viewModel: MarketViewModel = hiltViewModel(), onTickerClicked: (ticker :String) -> Unit , onSearchClicked:()->Unit) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
       // viewModel.fetchData("2024-06-27")
        viewModel.fetchData(timestampToDate(currentAdujestedTime))
    // Fetch data automatically when the screen is displayed
      //  Log.d("StockiMain", "getGroupedDailyBars ${viewModel.fetchData("2024-04-05")}")
      //  viewModel.fetchTickerLogo()

    }
    LaunchedEffect(Unit) {
        // Fetch ticker logos automatically when the screen is displayed
        val dataList = (state as? MarketState.Data)?.data ?: emptyList()
        val tickers = dataList.map { it.T }
       // viewModel.fetchTickerLogo(tickers)
    }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    when (val marketState = state) {

        is MarketState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is MarketState.Data -> {

            val dataList = marketState.data ?: emptyList()
          //  val logoMap = (state as? MarketState.Logo)?.logo // Retrieve logo map if available
            val categorizedTickers = categorizeByPrice(dataList)
            Column {
                val tabTitles = categorizedTickers.keys.toList()
                TopAppBar(
                    title = { Text(text = "STOCKI", modifier = Modifier.padding(start = 15.dp)) },
                    actions = {
                        IconButton(onClick = { onSearchClicked}) {
                            Icon(
                                painter = painterResource(id = R.drawable.searchdollar),
                                contentDescription = "Search",
                                tint = Color(0xFF0D151C)
                            )
                            Log.d("StockiMarket","IconButtons Clicked")
                        }

                    },
                    backgroundColor = Color(0xFFF8FAFC),
                    elevation = 0.dp)
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                backgroundColor = Color.Transparent
            ) {


                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.scrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title/*, fontSize = 10.sp*/,
                                modifier = Modifier
                                    .fillMaxWidth()
                            )
                        }
                    )

                }
            }
                CategorizedTickersPager(
                    categorizedTickers = categorizedTickers,
                    pagerState = pagerState,
                   onTickerClicked = onTickerClicked
                )
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
fun categorizeByPrice(tickers: List<AggregateData>): Map<String, List<AggregateData>> {
    return tickers.groupBy { ticker ->
        when (ticker.c) {
            in 0.0..50.0 -> "Low"
            in 50.0..200.0 -> "Mod"
            in 200.0..500.0 -> "High"
            in 500.0..1000.0 -> "V High"
            else -> {
                "Premium "
            }
        }
    }
}

@Composable
fun CardItem(aggregateData:AggregateData , onTickerClicked: (ticker: String) -> Unit) {
    Card(
        shape= RoundedCornerShape(10.dp),
        modifier = Modifier
            //  .border(5.dp, Color.Black)
            .padding(8.dp)
            .fillMaxWidth()
            .height(70.dp)
            .clickable {
                onTickerClicked(aggregateData.T)
                Log.d("StockiMarket", "onTickerClicked ${aggregateData.T}")
            }
    ) {
        Row(
            horizontalArrangement =  Arrangement.spacedBy(250.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(" ${aggregateData.T}", fontWeight = FontWeight.Bold)
            Text( "$" + aggregateData.c)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CategorizedTickersPager(
    categorizedTickers: Map<String, List<AggregateData>>,
    pagerState: PagerState,
     onTickerClicked: (ticker: String) -> Unit
) {
    val categories = categorizedTickers.keys.toList()

    HorizontalPager(state = pagerState, count = 5) { page ->
        val category = categories[page]
        val tickers = categorizedTickers[category] ?: emptyList()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(tickers) { ticker ->
                CardItem(aggregateData = ticker , onTickerClicked = onTickerClicked)
            }
        }
    }
}


