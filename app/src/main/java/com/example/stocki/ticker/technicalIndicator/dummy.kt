@file:Suppress("NAME_SHADOWING")

package com.example.stocki.ticker.technicalIndicator

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.geometry.Offset

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas

import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.PointF
import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stocki.market.bars.BarStates
import com.example.stocki.market.bars.BarsViewModel
import com.example.stocki.utility.Constans
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


@Composable
fun LineChartExample(stockTicker: String, viewModel:SmaViewModel = hiltViewModel(), barsviewModel: BarsViewModel = hiltViewModel(), onFetchData: (String) -> Unit ) {

    val state by viewModel.state.collectAsState()
    val barStates by barsviewModel.state.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    LaunchedEffect(isLandscape) {
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
            when (val barStates = barStates) {
                is BarStates.Data -> {
                    val timestamps = values.map { it.timestamp }
                    val timestampsclosing =  barStates.data!!.map { it.t }.reversed()
                    val closingPrices = barStates.data.map { it.c }.reversed()
                   // val smaValues = values.map { it.value }
                    val smaValues = values.map { String.format("%.2f", it.value).toDouble() }
                    //val formattedSmaValues = smaValues.map { "%.2f".format(it) }
                    val lineColors = listOf(Color.Blue, Color.Red)
                    val actualtime = Constans.timestampToDate(timestamps)
                    val actualtimeclosing = Constans.timestampToDate(timestampsclosing)

                    Log.d("stockisma", "closingPrices are $closingPrices")
                    Log.d("stockisma", "smaPrices are $smaValues")
                    Log.d("stockisma", "timestampsclosing are $actualtimeclosing")
                    Log.d("stockisma", "timestamps are $actualtime")


                    if (isLandscape) {
                        val density = Resources.getSystem().displayMetrics.density
                        val canvasWidth: Float = 720.dp.value * density
                        val canvasHeight: Float = 210.dp.value * density
                        val canvasSize = Size(canvasWidth, canvasHeight)
                        drawLines(timestamps, closingPrices, smaValues, canvasSize, lineColors)
                    }

                }

                BarStates.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                }
            }
        }
        is SmaState.Error -> {
            Text(smaState.error, modifier = Modifier.padding(16.dp))
            Log.e("stockiSMA", "ERROR ${smaState.error}")


        }
    }
}
@Composable
fun drawLines( timestamps: List<Long>, closingPrices: List<Double>, smaValues: List<Double>, canvasSize: Size, lineColors: List<Color>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
    val maxTimestamp = timestamps.maxOrNull() ?: 0L
    val minTimestamp = timestamps.minOrNull() ?: 0L
    val maxClosingPrice = closingPrices.maxOrNull() ?: 0.0
    val minClosingPrice = closingPrices.minOrNull() ?: 0.0
    val maxSmaValue = smaValues.maxOrNull() ?: 0.0
    val minSmaValue = smaValues.minOrNull() ?: 0.0

    val scaleX = canvasSize.width / (maxTimestamp - minTimestamp).toFloat()
    val scaleY1 = canvasSize.height / (maxClosingPrice - minClosingPrice).toFloat()
    val scaleY2 = canvasSize.height / (maxSmaValue - minSmaValue).toFloat()

    val closingPoints = timestamps.mapIndexed { index, timestamp ->
        val x = (timestamp - minTimestamp) * scaleX
        val y = canvasSize.height - (closingPrices[index] - minClosingPrice) * scaleY1
        PointF(x, y.toFloat())

    }
        Log.d("stockisma", "closingPrices are $closingPoints")
        val smaPoints = timestamps.mapIndexed { index, timestamp ->
        val x = (timestamp - minTimestamp) * scaleX
        val y = canvasSize.height - (smaValues[index] - minSmaValue) * scaleY2
        PointF(x, y.toFloat())
    }
        Log.d("stockisma", "smavalues are $smaPoints")
        drawPath(
            path = createPath(closingPoints),
            color = lineColors[0],
            style = Stroke(width = 2.dp.toPx()) // Adjust line thickness as needed
        )
        drawPath(
            path = createPath(smaPoints),
            color = lineColors[1],
            style = Stroke(width = 2.dp.toPx()) // Adjust line thickness as needed
        )
        /*// Draw closing prices line
        val closingPath = createPath(timestamps, closingPrices, minTimestamp, scaleX, scaleY1, canvasSize.height, minClosingPrice)
        drawPath(
            path = closingPath,
            color = lineColors[0],
            style = Stroke(width = 2.dp.toPx()) // Adjust line thickness as needed
        )

        // Draw SMA values line
        val smaPath = createPath(timestamps, smaValues, minTimestamp, scaleX, scaleY2, canvasSize.height, minSmaValue)
        drawPath(
            path = smaPath,
            color = lineColors[1],
            style = Stroke(width = 2.dp.toPx()) // Adjust line thickness as needed
        )*/
        drawaxis(timestamps, closingPrices, smaValues , canvasSize , closingPoints, smaPoints  )
    }


    }





fun DrawScope.drawaxis(
    timestamps: List<Long>,
    closingPrices: List<Double>,
    smaValues: List<Double>,
    canvasSize: Size,
    closingPoints: List<PointF>,
    smaPoints: List<PointF>

) {

        // Draw x-axis
        drawLine(
            color = Color.Black,
            start = Offset(0f, canvasSize.height),
            end = Offset(canvasSize.width, canvasSize.height),
            strokeWidth = 8.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Draw y-axis
        drawLine(
            color = Color.Black,
            start = Offset(0f, 0f),
            end = Offset(0f, canvasSize.height),
            strokeWidth = 8.dp.toPx(),
            cap = StrokeCap.Round
        )

   /* // Draw x-axis labels (timestamps)
    timestamps.forEachIndexed { index, timestamp ->
        val xPosition = index * (canvasSize.width / (timestamps.size - 1))
        val text = Constans.timestampToDate(timestamps)

        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                color = Color.Black.toArgb()
                textAlign = Paint.Align.CENTER
                textSize = 12f
            }
            canvas.nativeCanvas.drawText(text, xPosition, canvasSize.height + 15.dp.toPx(), paint)
        }
    }*/

   /*
    val maxPrice = maxOf(closingPrices.maxOrNull() ?: 0.0, smaValues.maxOrNull() ?: 0.0)
    val minPrice = minOf(closingPrices.minOrNull() ?: 0.0, smaValues.minOrNull() ?: 0.0)
    val priceRange = maxPrice - minPrice
    val priceLabels = listOf("0", "${minPrice.toInt()}", "${(minPrice + priceRange / 2).toInt()}", "${maxPrice.toInt()}")
    priceLabels.forEachIndexed { index, label ->
        val yPosition = index * (canvasSize.height / (priceLabels.size - 1))
        var textAlign = if (index % 2 == 0) Paint.Align.RIGHT else Paint.Align.LEFT
        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                color = Color.Black.toArgb()
                textAlign = textAlign
                textSize = 12f
            }
            canvas.nativeCanvas.drawText(label, if (textAlign == Paint.Align.RIGHT) 0f else canvasSize.width, yPosition, paint)
        }
    }*/

    // Draw x-axis labels (timestamps)
    closingPoints.forEachIndexed { index, point ->
      //  val text = Constans.timestampToDate(timestamps)
        val timestamp = timestamps[index]
        val instant = Instant.ofEpochMilli(timestamp)
        val dateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val formattedDateTime = formatter.format(dateTime)
        val text = formattedDateTime
        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                color = Color.Black.toArgb()
                textAlign = textAlign
                textSize = 12f
            }
            canvas.nativeCanvas.drawText(text, point.x, canvasSize.height + 15.dp.toPx(), paint)
        }
    }










    val maxValue = maxOf(closingPrices.maxOrNull() ?: 0.0, smaValues.maxOrNull() ?: 0.0)
    val labelCount = 10 //
    val labelInterval = maxValue / (labelCount - 1)

    for (i in 0 until labelCount) {
        val labelValue = i * labelInterval
        val yPosition = canvasSize.height - (labelValue / maxValue) * canvasSize.height // Scale the label position relative to the canvas size
        val priceLabel = "%.2f".format(labelValue) // Format the label value to display only two decimal places
        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                color = Color.Black.toArgb()
                textAlign = Paint.Align.RIGHT
                textSize = 12f // Adjust text size as needed
            }
            canvas.nativeCanvas.drawText(priceLabel, canvasSize.width + 15.dp.toPx(),
                yPosition.toFloat(), paint)
        }
    }



   /* // Draw y-axis labels (closing and SMA values)
    closingPoints.zip(smaPoints).forEachIndexed { index, (closingPoint, smaPoint) ->
        // Determine the price label for the current index
        val priceLabel = when {
            index % 2 == 0 -> closingPrices[index / 2].toString() // For closing prices
            else -> smaValues[index / 2].toString() // For SMA values
        }
        Log.d("stockiSma","$priceLabel")
        val yPosition = (closingPoint.y + smaPoint.y) / 2

        // Draw the label
        drawIntoCanvas { canvas ->
            val paint = Paint().apply {
                color = Color.Black.toArgb()
                textAlign = Paint.Align.CENTER
                textSize = 12f
            }
            canvas.nativeCanvas.drawText(priceLabel, canvasSize.width + 15.dp.toPx(), yPosition, paint)
        }
    }*/


}














private fun createPath(
    timestamps: List<Long>,
    values: List<Double>,
    minTimestamp: Long,
    scaleX: Float,
    scaleY: Float,
    canvasHeight: Float,
    minValue: Double
): Path {
    val points = timestamps.mapIndexed { index, timestamp ->
        val x = (timestamp - minTimestamp) * scaleX
        val y = canvasHeight - (values[index] - minValue) * scaleY
        PointF(x, y.toFloat())
    }
    Log.d("stockisma", "points are $points")

    return Path().apply {
        moveTo(points.first().x, points.first().y)
        points.forEach { point ->
            lineTo(point.x, point.y)
        }
    }
}

private fun createPath(points: List<PointF>): Path {
    return Path().apply {
        moveTo(points.first().x, points.first().y)
        points.forEach { point ->
            lineTo(point.x, point.y)
        }
    }
}

