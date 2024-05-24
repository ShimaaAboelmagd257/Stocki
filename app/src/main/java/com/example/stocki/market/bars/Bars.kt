package com.example.stocki.market.bars

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
/*

@Composable
fun AggregateBarChart(viewModel: BarsViewModel = hiltViewModel()) {
    // Assuming you have sorted the data by timestamp or some other relevant factor
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
       // viewModel.fetchData("AAPL",1,"day","2024-03-31","2024-04-09") // Fetch data automatically when the screen is displayed
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        when (val barState = state) {
            is BarStates.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            is BarStates.Data -> {
                val chartData = barState.data!!.mapIndexed { index, aggregateData ->
                    BarEntry(
                        index.toFloat(), // Use index as x value
                        ((aggregateData.c - aggregateData.l) / (aggregateData.h - aggregateData.l)).toFloat() // Use closing price for y value
                    )
                }
                HorizontalBarChart(data = chartData)
            }
            is BarStates.Error -> {
                // Show error message
                Text(
                    text = "Error: ${barState.error}",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            else -> {}
        }
    }
}
@Composable
fun HorizontalBarChart(data: List<BarEntry>) {

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            BarChart(context).apply {
                description.isEnabled = false
                setDrawGridBackground(false)
                setDrawBarShadow(false)
                isHighlightFullBarEnabled = false

                val barDataSet = BarDataSet(data, "Data Set").apply {
                    color = Color.Blue.toArgb()
                    setDrawValues(false)
                }

                val barData = BarData(barDataSet)
                barData.barWidth = 0.9f
                setData(barData)

                val xAxis = xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.setDrawGridLines(false)
                xAxis.setDrawAxisLine(true)
                xAxis.setDrawLabels(true)

                val yAxis = axisLeft
                yAxis.setDrawGridLines(true)
                yAxis.setDrawAxisLine(true)
                yAxis.setDrawLabels(true)

                legend.isEnabled = false
                axisLeft.isEnabled = false
                axisRight.isEnabled = false
            }
        }
    )
}

*/
