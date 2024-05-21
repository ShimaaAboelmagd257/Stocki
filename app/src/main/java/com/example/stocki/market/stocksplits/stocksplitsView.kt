package com.example.stocki.market.stocksplits

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





@Composable
fun SplitsView (viewModel: SplitsViewmodel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData() // Fetch data automatically when the screen is displayed
    }

    when (val splitsState = state) {
        is SplitsState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is SplitsState.Data -> {
            val dataList = splitsState.data
            LazyColumn {
                items(dataList.size) { index ->
                    val splits = dataList[index]
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("ticker: ${splits.ticker}")
                            Text("executionDate: ${splits.execution_date}")
                            Text("splitFrom Price: ${splits.split_from}")
                            Text("splitTo Price: ${splits.split_to}")

                        }
                    }
                }
            }
        }
        is SplitsState.Error -> {
            Text(splitsState.error, modifier = Modifier.padding(16.dp))
        }
    }


}


