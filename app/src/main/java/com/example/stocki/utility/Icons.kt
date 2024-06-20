package com.example.stocki.utility

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Canvas
import androidx.compose.material.Icon
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.stocki.R

@Preview
@Composable
fun StockMarketIcon(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    color: Color = Color.Black
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        // Outer circle
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(color = color, radius = size.toPx() / 2)
        }
        // Arrow lines
        Canvas(modifier = Modifier.matchParentSize()) {
            val halfSize = size / 2
            val strokeWidth = size / 10

            val startX = center.x - halfSize.toPx() * 0.3f
            val startY = center.y + halfSize.toPx() * 0.4f

            val endX = center.x
            val endY = center.y - halfSize.toPx() * 0.4f

            drawLine(
                color = color,
                start = Offset(startX, startY),
                end = Offset(endX, endY),
                strokeWidth = strokeWidth.toPx()
            )

            val arrowSize = size / 4

            val arrowStartX = center.x + halfSize.toPx() * 0.2f
            val arrowStartY = center.y

            val arrowEndX = center.x + arrowSize.toPx()
            val arrowEndY = center.y + arrowSize.toPx()

            drawLine(
                color = color,
                start = Offset(arrowStartX, arrowStartY),
                end = Offset(arrowEndX, arrowEndY),
                strokeWidth = strokeWidth.toPx()
            )

            val arrowStartX2 = center.x + halfSize.toPx() * 0.2f
            val arrowStartY2 = center.y

            val arrowEndX2 = center.x + arrowSize.toPx()
            val arrowEndY2 = center.y - arrowSize.toPx()

            drawLine(
                color = color,
                start = Offset(arrowStartX2, arrowStartY2),
                end = Offset(arrowEndX2, arrowEndY2),
                strokeWidth = strokeWidth.toPx()
            )
        }
    }
  /*  Icon(
        painter = painterResource(R.drawable.news),
        contentDescription = null
    )*/
}
object Icons{

}