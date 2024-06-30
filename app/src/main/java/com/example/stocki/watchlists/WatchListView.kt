package com.example.stocki.watchlists
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stocki.data.pojos.TickerTypes
import java.util.Collections.emptyList


@Composable
fun  WatchListView(viewModel: WatchListViewModel = hiltViewModel() , onInsertClicked: () -> Unit ) {

    val state by viewModel.watchListState.collectAsState()
    LaunchedEffect(Unit) {
        viewModel.fetchData()

    }

    when (val watchListsStates = state) {
        is WatchListsStates.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is WatchListsStates.Data -> {
            val watchLists = watchListsStates.data ?: emptyList()
            WatchList(tickers = watchLists , onInsertClicked = onInsertClicked , onRemoveClicked = {
                    ticker -> viewModel.removeTicker(ticker)} )
            LazyColumn {

            }
        }
        is WatchListsStates.Success->{


        }

        is WatchListsStates.Error -> {
            // Display error message
            Text(watchListsStates.error, modifier = Modifier.padding(16.dp))
        }
        is WatchListsStates.Deleting -> TODO()
    }

}

@Composable
fun WatchList(tickers: List<TickerTypes> , onInsertClicked: () -> Unit , onRemoveClicked: (TickerTypes) -> Unit ) {
    Box(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(150.dp))

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
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
                        IconButton(
                            onClick = { onRemoveClicked(ticker) },
                            modifier = Modifier.background(Color.Magenta , shape = CircleShape)
                                .padding(30.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = null,
                                tint = Color.Blue,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
        IconButton(
            onClick = { onInsertClicked() },
            modifier = Modifier.background(Color.Black , shape = CircleShape)
                .align(Alignment.BottomEnd)
                .padding(70.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                tint = Color.Blue,
                modifier = Modifier.size(50.dp)
            )
        }

    }

}
