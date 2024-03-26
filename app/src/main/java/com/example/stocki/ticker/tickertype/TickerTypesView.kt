package com.example.stocki.ticker.tickertype

import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun tickerView(viewModel: TickerViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    when (val tickerState = state) {
        is TickerTypeState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is TickerTypeState.Data -> {
            val tickerTypes = tickerState.data.sortedBy { it.asset_class }
                //.sortedBy { it.asset_class }

            LazyColumn {
                items(tickerTypes.size) { index ->
                    val tickerType = tickerTypes[index]
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Asset Class: ${tickerType.asset_class}")
                            Text("Description: ${tickerType.description}")
                        }
                    }
                }
            }
        }

        is TickerTypeState.Error -> {}


        else -> {}
    }
}