
package com.example.stocki.ticker.technicalIndicator
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import android.graphics.Paint
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.platform.LocalConfiguration
import com.example.stocki.market.bars.BarsViewModel
import com.example.stocki.utility.Constans.timestampsToDate
import com.madrapps.plot.line.DataPoint
import com.madrapps.plot.line.LinePlot
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import com.madrapps.plot.line.LinePlot.Grid
import com.madrapps.plot.line.LinePlot.Selection
import com.madrapps.plot.line.LinePlot.XAxis
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.times
import com.madrapps.plot.line.LineGraph

data class DataPointXLine(
    val xpos:Float,
    val ypos:Float,
    val value:Float
)
fun Dp.toPx(): Float {
    return this.value
}



@Composable
    fun SMAView(stockTicker: String, viewModel:SmaViewModel = hiltViewModel(), barsviewModel: BarsViewModel = hiltViewModel(), onFetchData: (String) -> Unit ) {

    val state by viewModel.state.collectAsState()
    val barStates by barsviewModel.state.collectAsState()
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val linePlotLines = remember { mutableStateListOf<LinePlot.Line>() }
    val dataPoints = arrayListOf<DataPoint>()
    val context = LocalContext.current
    // val dataPoints = mutableListOf<DataPoint>()
    //var xPos = 0f
    val dataValues = remember {
        mutableStateListOf<DataPointXLine>()
    }
    val textPaint = remember { mutableStateOf(Paint()) }
    LaunchedEffect(isLandscape) {
      //  context.ori = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        linePlotLines.clear()
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
            // when (val barStates = barStates) {
            //  is BarStates.Data -> {
            //val closingPrices = barStates.data!!.map { it.c }//.sorted().reversed()


            val timestamps = values.map { it.timestamp }
            val smaPrices = values.map { it.value.toFloat() }
            val minSmaPrice = smaPrices.minOrNull() ?: 0f
            val maxSmaPrice = smaPrices.maxOrNull() ?: 100f
            val priceRange = maxSmaPrice - minSmaPrice
            val yAxisSteps = 10


            val xLabels = timestampsToDate(timestamps)
            val labelCount = xLabels.size
            val totalWidth = 200.dp
            val stepSize = totalWidth / labelCount.toFloat()
            smaPrices.forEachIndexed { index, smaPrice ->
                val xPos = (index * stepSize).toPx()
                //+ stepSize.toPx()
                dataPoints.add(DataPoint(x = xPos, y = smaPrice))
                //stepSize += 20.dp
                Log.d("stockisma", "stepSize are ${stepSize}")


                //  Log.d("stockisma", "closingPrices are ${closingPrices}")
                Log.d("stockisma", "smaPrices are ${smaPrices}")
                Log.d("stockisma", "timestamps are ${timestamps}")
            }
            val xAxis = XAxis(
                stepSize = stepSize,
                steps = 10,
                unit = 1f,
                paddingTop = 6.dp,
                paddingBottom = 6.dp,
                roundToInt = false,
                content = { min, offset, max ->
                    repeat(10) { index ->
                        val xPos = (index * stepSize)
                        Text(
                            text = xLabels.getOrNull(index) ?: " ",//,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.padding(start = xPos) // Align the label with the corresponding point
                        )
                        Log.d("stockisma", "xAxis xLabels are ${xLabels}")
                        Log.d("stockisma", "xAxis stepSize are ${stepSize}")

                        /* if (value > max) {
                        break
                    }*/

                    }
                }
            )


            val yAxis = LinePlot.YAxis(
                steps = yAxisSteps,
                roundToInt = false, // If you want to display decimal values
                content = { min, offset, max ->
                    for (it in 0 until yAxisSteps) {
                        val value =
                            min + (priceRange / (yAxisSteps - 1)) * it.toFloat() // Calculate the value dynamically
                        Text(
                            text = value.toString(), // Format the value as needed
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface
                        )
                    }
                }
            )

            if (dataPoints.isNotEmpty()) {
                linePlotLines.add(
                    LinePlot.Line(
                        dataPoints,
                        LinePlot.Connection(color = Green),
                        LinePlot.Intersection(color = Black),
                        LinePlot.Highlight(color = Yellow),
                    )
                )

                Log.d("stockisma", "dataPoints.size are ${dataPoints.size}")


            }
            val linePlot = LinePlot(
                lines = linePlotLines,
                grid = Grid(Black, steps = 8),
                horizontalExtraSpace = 20.dp,
                 selection = Selection(
                    enabled = true,
                    highlight = LinePlot.Connection(
                        color = Black,
                        strokeWidth = 1.dp,
                        pathEffect = PathEffect.dashPathEffect(
                            floatArrayOf(2f, 1f)
                        )
                    )
                ),
                xAxis = xAxis,
                yAxis = yAxis
            )
            Log.d("stockisma", " linePlot.lines.size are ${linePlot.lines.size}")


            linePlot.lines.forEach {
                Log.d("stockisma", "linePlot.dataPoints are ${it.dataPoints}")

            }

            Card(
                modifier = Modifier

            ) {
                if (dataPoints.isNotEmpty() && isLandscape) {
                    LineGraph(
                        plot = linePlot,
                        modifier = Modifier,

                        onSelectionEnd = { dataPoints.clear() },
                        onSelection = { xLines, points ->
                            points.forEach {
                                dataValues.add(
                                    DataPointXLine(
                                        xpos = xLines,
                                        ypos = it.y,
                                        value = it.y
                                    )
                                )
                            }
                        }
                    )
                    Log.e("stockiSMA", "dataPoints.size" + dataPoints.size)


                } else {
                    Log.e("stockiSMA", "dataPoints.isEmpty()" + dataPoints.isEmpty())

                }
                /*  SampleLineGraph(
                            timestamps,
                            linePlotLines ,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp)
                        )*/
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(100.dp)
                        .drawBehind {
                            this.drawIntoCanvas {

                                val width = 10f
                                val hight = 3f
                                val yOffset = 10f

                                dataValues.forEach { datapoint ->
                                    drawRoundRect(
                                        color = Black,
                                        topLeft = Offset(
                                            x = datapoint.xpos - width / 2,
                                            y = yOffset
                                        ),
                                        size = Size(width, hight),
                                        cornerRadius = CornerRadius(
                                            x = 10f,
                                            y = 3f
                                        )
                                    )
                                    it.nativeCanvas.drawText(
                                        datapoint.ypos.toString(),
                                        datapoint.xpos,
                                        (yOffset / 10) + (hight / 10),
                                        textPaint.value

                                    )

                                }
                            }
                        }
                )

            }


            //}
            // else -> {}
            // }
            // }


        }
        is SmaState.Error -> {
            Text(smaState.error, modifier = Modifier.padding(16.dp))
            Log.e("stockiSMA", "ERROR ${smaState.error}")


        }

    }
}






























/*
@Composable
fun SampleLineGraph(
    timeStamps: List<Long>,
    linePlotLines: List<LinePlot.Line>,
    modifier: Modifier = Modifier,
    onSelection: (Float, List<DataPoint>) -> Unit = { _, _ -> }
) {
    val xLabels = timestampsToDate(timeStamps)
    //getXAxisLabels(linePlotLines.flatMap { it.dataPoints })
    var selectedSMAValue by remember { mutableStateOf<Float?>(null) }
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    LazyColumn {
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Column {
                    Box(modifier = modifier.height(300.dp)) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            drawIntoCanvas {
                                val canvas = it.nativeCanvas
                                // Draw Y-axis labels
                                val yAxisLabels = linePlotLines.flatMap { it.dataPoints.map { dataPoint -> dataPoint.y } }.distinct()
                                val yAxisLabelPositions = yAxisLabels.map { yLabel ->
                                    val yPosition = size.height - ((yLabel - linePlotLines.minOf { line -> line.dataPoints.minOf { it.y } }) / (linePlotLines.maxOf { line -> line.dataPoints.maxOf { it.y } } - linePlotLines.minOf { line -> line.dataPoints.minOf { it.y } })) * size.height
                                    yPosition to yLabel
                                }
                                yAxisLabelPositions.forEach { (yPosition, yLabel) ->
                                    canvas.drawText("$yLabel", 0f, yPosition, Paint().apply {
                                        color = Color.Black.toArgb()
                                        textSize = 20f // Customize text size as needed
                                        textAlign = Paint.Align.RIGHT
                                    })
                                }
                                // Draw X-axis labels
                                val xAxisLabelPositions = xLabels.mapIndexed { index, xLabel ->
                                    val xPosition = (index + 1) * (size.width / (xLabels.size + 1))
                                    xPosition.toFloat() to xLabel
                                }
                                xAxisLabelPositions.forEach { (xPosition, xLabel) ->
                                    canvas.drawText(xLabel, xPosition, size.height, Paint().apply {
                                        color = Color.Black.toArgb()
                                        textSize = 20f // Customize text size as needed
                                        textAlign = Paint.Align.CENTER
                                    })
                                }
                                linePlotLines.forEach { line ->
                                    val points = line.dataPoints
                                    val paint = Paint().apply {
                                        color = line.connection?.color?.toArgb()
                                            ?: Color.Black.toArgb()

                                        strokeWidth = 2.dp.toPx()
                                        isAntiAlias = true
                                    }

                                    val pointPaint = Paint().apply {
                                        color = line.connection?.color?.toArgb()
                                            ?: Color.Black.toArgb()

                                        isAntiAlias = true
                                    }

                                    // Draw lines and data points
                                    points.forEachIndexed { index, point ->
                                        val x = ((point.x - points.first().x) / (points.last().x - points.first().x)) * size.width
                                        val y = size.height - ((point.y - linePlotLines.minOf { it.dataPoints.minOf { it.y } }) / (linePlotLines.maxOf { it.dataPoints.maxOf { it.y } } - linePlotLines.minOf { it.dataPoints.minOf { it.y } })) * size.height

                                        // Draw data point
                                        canvas.drawCircle(x, y, 5.dp.toPx(), pointPaint)

                                        // Draw line connecting data points
                                        if (index > 0) {
                                            val startX = ((points[index - 1].x - points.first().x) / (points.last().x - points.first().x)) * size.width
                                            val startY = size.height - ((points[index - 1].y - linePlotLines.minOf { it.dataPoints.minOf { it.y } }) / (linePlotLines.maxOf { it.dataPoints.maxOf { it.y } } - linePlotLines.minOf { it.dataPoints.minOf { it.y } })) * size.height
                                            canvas.drawLine(startX, startY, x, y, paint)
                                        }
                                    }
                                }
                            }
                        }
                        selectedIndex?.let { index ->
                            selectedSMAValue?.let { smaValue ->
                                Text(
                                    text = "SMA: $smaValue",
                                    modifier = Modifier
                                        .align(Alignment.TopStart)
                                        .padding(16.dp),
                                    color = Color.Black
                                )
                            }
                        }

                }
            }
        }
    }
}
    val handleSelection: (Float, List<DataPoint>) -> Unit = { x, dataPoints ->
        // Calculate index of the closest data point to the selected X-coordinate
        val index = dataPoints.indexOfFirst { it.x == x }

        // Set selected index
        selectedIndex = index

        // Retrieve SMA value for the selected index
        selectedSMAValue = if (index != -1) {
            linePlotLines.firstOrNull()?.dataPoints?.get(index)?.y // Assuming only one line plot
        } else {
            null
        }

        // Callback function
        onSelection(x, dataPoints)
    }
  //  onSelection(handleSelection)

    // Call the actual onSelection function with the modified handleSelection
}


*/

