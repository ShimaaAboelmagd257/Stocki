@file:OptIn(ExperimentalPagerApi::class)

package com.example.stocki.ticker.tickerinfo

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stocki.R
import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.Company
import com.example.stocki.data.pojos.WatchList
import com.example.stocki.data.pojos.account.PortfolioItem
import com.example.stocki.utility.Constans
import com.example.stocki.watchlists.WatchListViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
fun aggregateDataToWatchList(aggregateData: AggregateData): WatchList {
    return WatchList(
        T = aggregateData.T,
        c = aggregateData.c,
        h = aggregateData.h,
        l = aggregateData.l,
        absoluteChangeFormatted = aggregateData.c - aggregateData.o,  // Example calculation
        percentageChangeFormatted = ((aggregateData.c - aggregateData.o) / aggregateData.o) * 100  // Example calculation
    )
}
@Composable
 fun tickerInfoView(ticker: String,
                           viewModel: TickerInfoViewModel = hiltViewModel(),
                           watchListViewModel: WatchListViewModel = hiltViewModel(),
                           onTrading: (ticker:String,price:Double) -> Unit,
                           userId:String

) {
    val state by viewModel.state.collectAsState()
    LaunchedEffect(ticker) {
        viewModel.fetchTickerData(ticker)
        Log.d("StockitickerInfoView", "onFetchData ${ticker}")
    }
    when (val tickerInfoState = state) {
        is TickerInfoState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(150.dp , 300.dp))
        }
        is TickerInfoState.CombinedData -> {
            val companies = tickerInfoState.companies
            val aggregate = tickerInfoState.aggregateData
            val scrollState = rememberScrollState()
          //  val aggregateData = aggregate
            Column(modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF8FAFC))
                .verticalScroll(scrollState)
                ) {
                TopAppBar(
                    title = {  },
                    actions = {
                        IconButton(onClick = { watchListViewModel.insertData(aggregateDataToWatchList(aggregate))}) {
                            Icon(
                                painter = painterResource(id = R.drawable.watchlistunselected),
                                contentDescription = "Search",
                                tint = Color(0xFF0D151C)
                            )
                        }
                    },
                    backgroundColor = Color(0xFFF8FAFC),
                    elevation = 0.dp)
                AggregateTickerData(
                    aggregate = aggregate,
                )
                TickerInfo(company =companies, aggregate=aggregate,viewModel = viewModel,
                    userId = userId,
                    onTrading ={ onTrading(ticker,aggregate.c) },
                )
            }

        }
        is TickerInfoState.Error -> {
            Log.d("StockitickerInfoState", "tickerInfoState  ${tickerInfoState.error}")
            Text(tickerInfoState.error, modifier = Modifier.padding(200.dp , 300.dp))
        }

    }
}

@Composable
fun CardItem(title:String,price:Double) {
    Card(modifier = Modifier
        .padding(8.dp)
        .width(130.dp)
        .height(100.dp),
        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = title, style = MaterialTheme.typography.h6)
            Text(text = "${price}", style = MaterialTheme.typography.body1)

        }
    }
}
@Composable
fun AggregateTickerData (
    aggregate: AggregateData
){
    val items = listOf(
        "High price" to aggregate.h,
        "Low price" to aggregate.l,
        "Opening price" to aggregate.o,
        "Volume" to aggregate.v,
        "Volume Weighted Average Price" to aggregate.vw
    )
    Card(
        shape=RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = aggregate.T,
                style = MaterialTheme.typography.h4.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF0D151C)
                )
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                "$"+aggregate.o, style = MaterialTheme.typography.h5,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimaryContainer
            )

        }
        Spacer(modifier = Modifier.height(16.dp))
    }
    Text(text = "Key Statistics")
    Spacer(modifier = Modifier.height(16.dp))

    LazyRow(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){
        items(items){ item ->
            CardItem(title = item.first, price = item.second)
        }
    }
    Spacer(modifier = Modifier.height(16.dp))

}

@Composable
fun TickerInfo(company: List<Company>,    aggregate: AggregateData, viewModel: TickerInfoViewModel,
               userId: String,
               onTrading: () -> Unit,
) {
    val tabTitels =
        listOf("About", "Shares", "Contact", "More")
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

   // val scrollState = rememberScrollState()
    company?.forEach { company->
        val branding = company.branding
        val logoUrl = branding?.logo_url
        val iconUrl = branding?.icon_url
        if (logoUrl != null) {
            Constans.LoadSvgImageWithFallback(
                imageUrl = "$logoUrl?apiKey=${Constans.Api_Key}",
                fallbackImage = painterResource(id = R.drawable.bussniss),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
        }

        if (iconUrl != null) {
            Constans.LoadpngImageWithFallback(
                imageUrl = "$iconUrl?apiKey=${Constans.Api_Key}",
                fallbackImage = painterResource(id = R.drawable.check),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.Black)
            )
        }
}
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier.fillMaxWidth()
    ) {
        Button(onClick = {
            viewModel.buyStock(
                userId, PortfolioItem(
                    ticker = aggregate.T,
                    quantity = 1,
                    averagePrice = aggregate.vw
                )
            )
            onTrading()
        },colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2593F4)), modifier = Modifier.width(120.dp)) {
            Text(text = "Buy")
        }
        Button(onClick = {
            viewModel.sellStock(
                userId,
                aggregate.T,
                1,
                aggregate.c
            )
            onTrading()
        } , colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFE7EEF4)), modifier = Modifier.width(120.dp)
        ) {
            Text(text = "Sell")
        }

    }
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        modifier = Modifier.background(androidx.compose.material3.MaterialTheme.colorScheme.surface),
        backgroundColor = Color.Transparent
    ) {

        tabTitels.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.scrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = title,
                        modifier = Modifier
                            .fillMaxWidth(),
                    )
                }
            )

        }
    }
    company?.forEach { company ->
        HorizontalPager(state = pagerState, count = 4) { page ->
            when (page) {
                0 -> GeneralDetails(company)
                1 -> FinancialOverview(company)
                2 -> ContactAndLocation(company)
                3 -> AdditionalDetails(company)

            }
        }

}
}
    @Composable
    fun GeneralDetails(company: Company) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Company Name: ${company.name}", fontWeight = FontWeight.Bold)
            Text(text = "Market: ${company.market}")
            Text(text = "Locale: ${company.locale}")
            Text(text = "Primary Exchange: ${company.primary_exchange}")
            Text(text = "Type: ${company.type}")
            Text(text = "Ticker: ${company.ticker}")
                 Text(text = "Active: ${company.active}")
            Text(text = "Currency Name: ${company.currency_name}")
            Text(text = "CIK: ${company.cik}")
            Text(text = "Ticker Root: ${company.ticker_root}")
        }
    }

    @Composable
    fun FinancialOverview(company: Company) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Market Cap: ${company.market_cap}", fontWeight = FontWeight.Bold)
            Text(text = "Share Class Shares Outstanding: ${company.share_class_shares_outstanding}")
            Text(text = "Weighted Shares Outstanding: ${company.weighted_shares_outstanding}")
            Text(text = "Round Lot: ${company.round_lot}")
        }
    }

    @Composable
    fun ContactAndLocation(company: Company) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Phone Number: ${company.phone_number}", fontWeight = FontWeight.Bold)
            Text(text = "Address: ${company.address?.address1}, ${company.address?.city}, ${company.address?.state}, ${company.address?.postal_code}")
            Text(text = "Homepage URL: ${company.homepage_url}")
        }
    }

    @Composable
    fun AdditionalDetails(company: Company) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Description: ${company.description}", fontWeight = FontWeight.Bold)
            Text(text = "SIC Code: ${company.sic_code}")
            Text(text = "SIC Description: ${company.sic_description}")
            Text(text = "List Date: ${company.list_date ?: "Not available"}")
            Text(text = "Composite FIGI: ${company.composite_figi}")
            Text(text = "Share Class FIGI: ${company.share_class_figi}")
            Text(text = "Total Employees: ${company.total_employees}")
        }
    }


