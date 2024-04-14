package com.example.stocki.ticker.technicalIndicator
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import com.example.stocki.market.bars.BarStates
import com.example.stocki.market.bars.BarsViewModel
import java.time.Instant
import java.time.Month
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
    fun SMAView(stockTicker: String, viewModel:SmaViewModel = hiltViewModel(), barsviewModel: BarsViewModel = hiltViewModel(), onFetchData: (String) -> Unit ){

        val state by viewModel.state.collectAsState()
        val barStates by barsviewModel.state.collectAsState()
        val configuration = LocalConfiguration.current
         val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

        LaunchedEffect(isLandscape){
            if (isLandscape) {
            onFetchData(stockTicker)
            }
        }

        when (val smaState = state) {
            is SmaState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
            is SmaState.Data -> {

                val smaResponse = smaState.data
                val values = smaResponse.results.values
                    when(val barStates = barStates){
                        is BarStates.Data -> {
                            val closingPrices = barStates.data!!.map { it.c }//.sorted().reversed()
                            val timestamps = values.map { it.timestamp }
                            val smaPrices = values.map { it.value }
                            Log.d("stockisma","closingPrices are ${closingPrices}")
                            Log.d("stockisma","smaPrices are ${smaPrices}")

                            Log.d("stockisma","timestamps are ${timestamps}")

                            if (isLandscape) {
                                LineChart(
                                    closingPrices = closingPrices,
                                    timestamps = timestamps,
                                    smaValues = smaPrices,
                                    chartWidth = 700.dp,
                                    chartHeight = 200.dp
                                )
                            }
                        }
                        BarStates.Loading -> TODO()
                    }
            }
            is SmaState.Error -> {
                Text(smaState.error, modifier = Modifier.padding(16.dp))
                Log.e("stockiSMA", "ERROR ${smaState.error}")


            }
        }
    }


    @Composable
    fun LineChart(
        closingPrices: List<Double>,
        timestamps: List<Long>,
        smaValues: List<Double>,
        chartWidth: Dp,
        chartHeight: Dp
    ) {
        Canvas(modifier = Modifier.size(chartWidth, chartHeight)) {

            val minX = timestamps.minOrNull() ?: 0L
            val maxX = timestamps.maxOrNull() ?: 0L
            val minY = minOf(closingPrices.minOrNull() ?: 0.0, smaValues.minOrNull() ?: 0.0)
            val maxY = maxOf(closingPrices.maxOrNull() ?: 0.0, smaValues.maxOrNull() ?: 0.0)

            val scaleX = size.width / (maxX - minX).toFloat()
            val scaleY = size.height / (maxY - minY).toFloat()

            drawAxes(minX, maxX, minY, maxY, scaleX, scaleY,timestamps,closingPrices,smaValues)
            drawLineGraph(closingPrices, timestamps, scaleX, scaleY, minX, minY, Color.Blue)
            drawLineGraph(smaValues, timestamps, scaleX, scaleY, minX, minY, Color.Red)

        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun DrawScope.drawAxes(
        minX: Long,
        maxX: Long,
        minY: Double,
        maxY: Double,
        scaleX: Float,
        scaleY: Float,
        timestamps: List<Long>,
        closingPrices: List<Double>,
        smaValues: List<Double>
    ) {
        // Draw x-axis
        drawLine(
            color = Color.Black,
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height)
        )

        // Draw y-axis
        drawLine(
            color = Color.Black,
            start = Offset(0f, size.height),
            end = Offset(0f, size.height)
        )


        // Draw x-axis labels
        timestamps.forEachIndexed { index, timestamp ->
                val instant = Instant.ofEpochMilli(timestamp)
                val dateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
                val formattedDateTime = formatter.format(dateTime)
                println("Timestamp ploting $timestamp corresponds to: $formattedDateTime")

            val xPos = (timestamp - minX) * scaleX
            val yPos = size.height + 5.dp.toPx()

           /* // Rotate canvas for x-axis labels
            rotate(-45f, pivot = Offset(xPos, yPos)) {
                drawLine(
                    color = Color.Black,
                    start = Offset(xPos, size.height),
                    end = Offset(xPos, yPos)
                )*/
                // Draw timestamp label
                val text = formattedDateTime
                drawIntoCanvas { canvas ->
                    val paint = Paint().apply {
                        color = Color.Black.toArgb()
                        textAlign = Paint.Align.CENTER
                        textSize = 12f // Adjust text size as needed
                    }
                    canvas.nativeCanvas.drawText(text, xPos, size.height + 15.dp.toPx(), paint)
                }
            }
        //}
        // Calculate the step size for y-axis labels
        val yAxisStep = size.height / (closingPrices.size - 1)


        /*// Draw y-axis labels for closing prices
        closingPrices.forEachIndexed { index, price ->
            val y = size.height - index * yAxisStep
            val x = -40f // Position to the left of the y-axis

            drawLine(
                color = Color.Black,
                start = Offset(0f, y),
                end = Offset(-5f, y)
            )

            // Draw closing price label
            val text = String.format("%.2f", price) // Format closing price to 2 decimal places
            drawIntoCanvas {
                it.nativeCanvas.drawText(text, x, y, Paint().apply {
                    color = Color.Black.toArgb()
                    textAlign = Paint.Align.RIGHT
                    textSize = 12f // Adjust text size as needed
                })
            }
        }

        // Draw y-axis labels for SMA values
        smaValues.forEachIndexed { index, value ->
            val y = size.height - index * yAxisStep
            val x = size.width + 40f // Position to the right of the chart

            drawLine(
                color = Color.Black,
                start = Offset(size.width, y),
                end = Offset(size.width + 5f, y)
            )

            // Draw SMA value label
            val text = String.format("%.2f", value) // Format SMA value to 2 decimal places
            drawIntoCanvas {
                it.nativeCanvas.drawText(text, x, y, Paint().apply {
                    color = Color.Black.toArgb()
                    textAlign = Paint.Align.LEFT
                    textSize = 12f // Adjust text size as needed
                })
            }
        }*/
        // Determine the maximum width needed for the labels
        val closingPriceLabelsWidth = closingPrices.maxOf { price ->
            val textWidth = getTextWidth(String.format("%.2f", price), 12f)
            textWidth
        }

        val smaLabelsWidth = smaValues.maxOf { value ->
            val textWidth = getTextWidth(String.format("%.2f", value), 12f)
            textWidth
        }

// Determine the maximum width between closing prices and SMA values
        val maxWidth = maxOf(closingPriceLabelsWidth, smaLabelsWidth)

// Adjust the x-coordinate for closing prices and SMA values labels
        val closingPriceLabelX = -maxWidth - 10f // Position to the left of the y-axis
        val smaLabelX = size.width + 10f // Position to the right of the chart

// Draw y-axis labels for closing prices
        closingPrices.forEachIndexed { index, price ->
            val y = size.height - index * yAxisStep

            // Draw line marker for closing prices
            drawLine(
                color = Color.Black,
                start = Offset(0f, y),
                end = Offset(-5f, y)
            )

            // Draw closing price label
            val text = String.format("%.2f", price) // Format closing price to 2 decimal places
            drawIntoCanvas {
                it.nativeCanvas.drawText(text, closingPriceLabelX, y, Paint().apply {
                    color = Color.Black.toArgb()
                    textAlign = Paint.Align.RIGHT
                    textSize = 12f // Adjust text size as needed
                })
            }
        }

// Draw y-axis labels for SMA values
        smaValues.forEachIndexed { index, value ->
            val y = size.height - index * yAxisStep

            // Draw line marker for SMA values
            drawLine(
                color = Color.Black,
                start = Offset(size.width, y),
                end = Offset(size.width + 5f, y)
            )

            // Draw SMA value label
            val text = String.format("%.2f", value) // Format SMA value to 2 decimal places
            drawIntoCanvas {
                it.nativeCanvas.drawText(text, smaLabelX, y, Paint().apply {
                    color = Color.Black.toArgb()
                    textAlign = Paint.Align.LEFT
                    textSize = 12f // Adjust text size as needed
                })
            }
        }

// Provide legends or labels to distinguish between closing prices and SMA values
        val legendY = size.height / 2f
        val legendX = size.width / 2f

        drawIntoCanvas {
            // Draw legend for closing prices
            it.nativeCanvas.drawText("Closing Prices", legendX, legendY, Paint().apply {
                color = Color.Black.toArgb()
                textAlign = Paint.Align.RIGHT
                textSize = 14f // Adjust text size as needed
            })

            // Draw legend for SMA values
            it.nativeCanvas.drawText("SMA Values", legendX, legendY + 20f, Paint().apply {
                color = Color.Black.toArgb()
                textAlign = Paint.Align.RIGHT
                textSize = 14f // Adjust text size as needed
            })
        }



    }
// Function to calculate the width of a text string
private fun getTextWidth(text: String, textSize: Float): Float {
    val paint = Paint()
    paint.textSize = textSize
    return paint.measureText(text)
}

private fun DrawScope.drawLineGraph(
            values: List<Double>,
            timestamps: List<Long>,
            scaleX: Float,
            scaleY: Float,
            minX: Long,
            minY: Double,
            color: Color
        ) {
            val path = Path()
            for (i in values.indices) {
                val x = ((timestamps[i] - minX) * scaleX)
                val y = size.height - ((values[i] - minY) * scaleY).toFloat()
                if (i == 0) {
                    path.moveTo(x, y)
                } else {
                    path.lineTo(x, y)
                }
            }
            drawPath(path, color, alpha = 1f, style = Stroke(width = 2f))
        }
