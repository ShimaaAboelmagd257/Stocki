package com.example.stocki.holidays

import android.util.Log
import androidx.compose.foundation.clickable
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
fun HolidaysView(viewModel: HolidaysViewModel = hiltViewModel()){

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit){
        viewModel.fetchData()
    }

    when (val holidaysStates = state) {
        is HolidaysStates.Loading -> {
            // Display loading indicator
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is HolidaysStates.Data -> {
            val dataList = holidaysStates.data ?: emptyList()
            LazyColumn {
                items(dataList.size) { index ->
                    val holidays = dataList[index]
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()

                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Text("Date: ${holidays.date}")
                            Text("Exchange: ${holidays.exchange}")
                            Text("Name: ${holidays.name}")
                            Text("Status: ${holidays.status}")
                            Text("Open: ${holidays.open}")
                            Text("Closed: ${holidays.close}")
                        }
                    }
                }
            }
        }
        is HolidaysStates.Error -> {
            // Display error message
            Text(holidaysStates.error, modifier = Modifier.padding(16.dp))
        }
    }
}