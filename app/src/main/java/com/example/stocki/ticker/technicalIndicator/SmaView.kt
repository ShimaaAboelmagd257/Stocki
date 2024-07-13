
package com.example.stocki.ticker.technicalIndicator
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import android.graphics.Paint
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
import androidx.compose.ui.graphics.Color.Companion.Red
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
    //val barStates by barsviewModel.state.collectAsState()
    //val configuration = LocalConfiguration.current
    //val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    val linePlotLines = remember { mutableStateListOf<LinePlot.Line>() }
    val dataPoints = arrayListOf<DataPoint>()
    //val context = LocalContext.current
    // val dataPoints = mutableListOf<DataPoint>()
    //var xPos = 0f
    val dataValues = remember {
        mutableStateListOf<DataPointXLine>()
    }
    val textPaint = remember { mutableStateOf(Paint()) }
    LaunchedEffect(Unit) {
      //  context.ori = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        linePlotLines.clear()
            onFetchData(stockTicker)

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
          //  val labelCount = xLabels.size
           // val totalWidth = 100.dp
           // val stepSize = totalWidth / labelCount.toFloat()
            smaPrices.forEachIndexed { index, smaPrice ->
                val xPos = (index * 5.dp).toPx()
                dataPoints.add(DataPoint(x = xPos, y = smaPrice))


                Log.d("stockisma", "stepSize are ${index}")
                //  Log.d("stockisma", "closingPrices are ${closingPrices}")
                Log.d("stockisma", "smaPrices are ${smaPrices}")
                Log.d("stockisma", "timestamps are ${timestamps}")
            }
            val xAxis = XAxis(
              //  stepSize = ,
                steps = 10,
                unit = 1f,
                paddingTop = 6.dp,
                paddingBottom = 6.dp,
                roundToInt = false,
                content = { min, offset, max ->
                    repeat(10) { index ->
                        //val xPos = (index * stepSize).toPx()
                        Text(
                            text = xLabels.getOrNull(index) ?: " ",//,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.caption,
                            color = MaterialTheme.colors.onSurface,
                            modifier = Modifier.padding(start = index*5.dp) // Align the label with the corresponding point
                        )
                        Log.d("stockisma", "xAxis xLabels are ${xLabels}")
                        Log.d("stockisma", "xAxis stepSize are ${index}")
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
                grid = Grid(Black, steps = 9),
                horizontalExtraSpace = 0.dp,
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
                modifier = Modifier.height(300.dp).fillMaxWidth(), backgroundColor = Red


            ) {
                if (dataPoints.isNotEmpty() ) {
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

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                        .drawBehind {
                            this.drawIntoCanvas {
                                val width = 3f
                                val hight = 0.5f
                                val yOffset = 1.5f
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
        }
        is SmaState.Error -> {
            Text(smaState.error, modifier = Modifier.padding(16.dp))
            Log.e("stockiSMA", "ERROR ${smaState.error}")


        }

    }
}





























