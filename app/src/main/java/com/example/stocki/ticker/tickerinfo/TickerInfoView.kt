package com.example.stocki.ticker.tickerinfo

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import coil.compose.rememberImagePainter
import com.example.stocki.TabBarItem
import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.Company
import com.example.stocki.data.pojos.account.PortfolioItem
import com.example.stocki.utility.Constans
import com.example.stocki.watchlists.WatchListViewModel

private val _toastMessage = MutableLiveData<String>()
val toastMessage: LiveData<String> get() = _toastMessage

@Composable
 fun tickerInfoView(ticker: String,
                           viewModel: TickerInfoViewModel = hiltViewModel(),
                           watchListViewModel: WatchListViewModel = hiltViewModel(),
                           onTrading: () -> Unit,
                           userId:String

) {

    val state by viewModel.state.collectAsState()
    //val tradeState by viewModel.tradeState.collectAsState()
   // val watchListState by watchListViewModel.watchListState.collectAsState()

    LaunchedEffect(ticker) {
     //  viewModel.fetchData(ticker)
       // viewModel.fetchTickerItemById(ticker)
        viewModel.fetchTickerData(ticker)
        Log.d("StockitickerInfoView", "onFetchData ${ticker}")
    }
    when (val tickerInfoState = state) {
        is TickerInfoState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(150.dp , 300.dp))
        }
        is TickerInfoState.Aggriigate -> {

        }
        is TickerInfoState.Data -> {



            Log.d("StockitickerInfoState", "tickerInfoStateData  ${tickerInfoState.data}")

        }
        is TickerInfoState.CombinedData -> {
            val companies = tickerInfoState.companies
            val aggregate = tickerInfoState.aggregateData
            Column() {
                AggregateTickerData(
                    aggregate = aggregate,
                    viewModel = viewModel,
                    userId = userId,
                    onTrading =onTrading,
                    watchListViewModel = watchListViewModel
                )
                TickerInfo(company =companies , viewModel = viewModel, userId =userId )
            }

        }

        is TickerInfoState.Error -> {
            Log.d("StockitickerInfoState", "tickerInfoState  ${tickerInfoState.error}")
            Text(tickerInfoState.error, modifier = Modifier.padding(150.dp , 300.dp))

        }

    }
}

@Composable
fun AggregateTickerData (aggregate: AggregateData,
                         viewModel: TickerInfoViewModel,
                         userId: String,
                         onTrading: () -> Unit,
                         watchListViewModel: WatchListViewModel) {
  //  val aggrigate = tickerInfoState.data
  //  val tickerPrices =  viewModel.fetchTickerItemById(ticker)

    Card(
        modifier = Modifier.border(5.dp, Color.Black)
            .padding(10.dp)
            .fillMaxWidth()

    ) {
        Column(
            //mor.paddidifier = Modifieng(16.dp)
        ) {

            Text(" ${aggregate.T}", fontWeight = FontWeight.Bold)
            Text("Opening Price: " + aggregate.o)
            Text("Closing Price: ${aggregate.c}")
            Text("High Price: ${aggregate.h}")
            Text("Low Price: ${aggregate.l}", color = Color.Red)
        }
    }
    Button(onClick = {
        viewModel.buyStock(userId,PortfolioItem(
        ticker = aggregate.T,
        quantity = 1,
        averagePrice = aggregate.vw
    ))
        onTrading()
    } ) {
        Text(text = "Buy")
        _toastMessage.postValue("congratulations! you just buy a stock")

    }
    Button(onClick = {
        viewModel.sellStock(
            userId,
            aggregate.T,
            1,
            aggregate.c)
        onTrading()
    }) {
        Text(text = "Sell")
        _toastMessage.postValue(" you just sell a stock")

    }


    //

    IconButton(onClick = {
        watchListViewModel.insertData(aggregate)
    }
    ){
        Icon(Icons.Default.Add, contentDescription = "Add to Watchlist")
        _toastMessage.postValue("Ticker is added to your watchlist")

    }

}

@Composable
fun TickerInfo(company: List<Company>,
               viewModel: TickerInfoViewModel,
               userId: String
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {


    company?.forEach { company ->
        /*Constans.LoadNetworkSvgImage(
            url = "${company.branding.logo_url}?apiKey=${Constans.Api_Key}"  ,
             modifier = Modifier
                 .fillMaxWidth()
                 .height(200.dp)
                 .clip(RoundedCornerShape(8.dp))
        )*/
        //   if(tradeState.)

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
}