package com.example.stocki.market.status

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stocki.holidays.HolidaysStates
import com.example.stocki.holidays.HolidaysViewModel

@Composable
fun MarketStatusView(viewModel: StatusViewmodel = hiltViewModel()){

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchData()
    }

    when (val statusState = state) {
        is StatusState.Loading -> {
            // Display loading indicator
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is StatusState.Data -> {
            val marketStatusResponse = statusState.data

            LazyColumn {
                item {
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Market: ${marketStatusResponse.market}")
                            Text("Server Time: ${marketStatusResponse.serverTime}")
                            Text("Exchanges:")
                            Text("NASDAQ: ${marketStatusResponse.exchanges.nasdaq}")
                            Text("NYSE: ${marketStatusResponse.exchanges.nyse}")
                            Text("OTC: ${marketStatusResponse.exchanges.otc}")
                            Text("Currencies:")
                            Text("crypto: ${marketStatusResponse.currencies.crypto}")
                            Text("fx: ${marketStatusResponse.currencies.fx}")
                        }
                    }
                }
            }
        }
        is StatusState.Error -> {
            // Display error message
            Text(statusState.error, modifier = Modifier.padding(16.dp))
        }
    }
}