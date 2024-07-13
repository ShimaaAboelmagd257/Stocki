package com.example.stocki.splash

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.PagerState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
//import kotlin.
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.stocki.ticker.technicalIndicator.toPx
import java.lang.Math.*
import kotlin.math.sin

// To get scroll offset
//@OptIn(ExperimentalPagerApi::class)
@OptIn(ExperimentalFoundationApi::class)
val PagerState.pageOffset: Float
    get() = this.currentPage + this.currentPageOffsetFraction


// To get scrolled offset from snap position
//@OptIn(ExperimentalPagerApi::class)
@OptIn(ExperimentalFoundationApi::class)
fun PagerState.calculateCurrentOffsetForPage(page: Int): Float {
    return (currentPage - page) + currentPageOffsetFraction
}
private fun DrawScope.drawIndicator(
    x: Float,
    y: Float,
    width: Float,
    height: Float,
    radius: CornerRadius
) {
    val rect = RoundRect(
        x,
        y - height / 2,
        x + width,
        y + height / 2,
        radius
    )
    val path = Path().apply { addRoundRect(rect) }
    drawPath(path = path, color = White)
}
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.drawIndicator(
    pagerState: PagerState,
    index: Int
) = drawBehind {
    val dotSpacing = 16.dp.toPx() // Adjust based on spacing between dots
    val dotRadius = 6.dp.toPx() // Adjust based on dot size
    val distance = 24.dp.toPx() // Adjust based on dot size + spacing
    val settledPage = pagerState.settledPage
    val currentPage = pagerState.currentPage
    val pageOffset = pagerState.currentPageOffsetFraction
    val currentX = index * (dotSpacing + 2 * dotRadius)
    val targetX = currentPage * (dotSpacing + 2 * dotRadius) + pageOffset * (dotSpacing + 2 * dotRadius)

    val dotColor = if (index == currentPage) Color.Black else Color.Gray
    drawCircle(color = dotColor, radius = dotRadius, center = Offset(currentX, size.height - dotRadius))

    // Calculate the bounce effect
    val absPageOffset = abs(pageOffset)
    val scale = if (absPageOffset < .5) {
        1.0f + (absPageOffset * 2) * 0.5f // Adjust the amplitude of bounce
    } else {
        1.5f + ((1 - absPageOffset) * 2) * 0.5f // Adjust the amplitude of bounce
    }
    val factor = absPageOffset * PI.toFloat()
    val yOffset = sin(factor) * 10.dp.toPx() // Adjust the bounce distance

    // Apply the bounce effect
    drawCircle(
        color = dotColor,
        radius = dotRadius * scale,
        center = Offset(currentX, size.height - dotRadius - yOffset)
    )
}
@OptIn(ExperimentalFoundationApi::class)
fun Modifier.bounceDotTransition(
    pagerState: PagerState,
    jumpOffset: Float,
    jumpScale: Float
) =
    graphicsLayer {

        val targetScale = jumpScale - 1f
        val distance = size.width + 8.dp.roundToPx()
        val pageOffset = pagerState.currentPageOffsetFraction
        val abs = abs(pageOffset)
        val scrollPosition = pagerState.currentPage + pageOffset
        val current = scrollPosition.toInt()
        val settledPage = pagerState.settledPage

        translationX = scrollPosition * distance

        val scale = if (abs < .5) {
            1.0f + (abs * 2) * targetScale;
        } else {
            jumpScale + ((1 - (abs* 2)) * targetScale);
        }

        scaleX = scale
        scaleY = scale

        val factor = (abs * Math.PI)
        val y =
            if (current >= settledPage) -sin(factor) * jumpOffset else sin(factor) * distance / 2
        translationY += y.toFloat()
        Log.d("Transtion","${settledPage}")
    }
/*@OptIn(ExperimentalFoundationApi::class)
fun Modifier.drawIndicator(
    pagerState: PagerState,
    index: Int
) = drawBehind {
    val dotSpacing = 16.dp.toPx() // Adjust based on spacing between dots
    val dotRadius = 6.dp.toPx() // Adjust based on dot size
    val distance = 24.dp.toPx() // Adjust based on dot size + spacing
  //  val pageOffset = pagerState.calculateCurrentOffsetForPage(index)
  //  val absPageOffset = abs(pageOffset)
 //    val scrollPosition = pagerState.currentPage + pageOffset
   // val current = scrollPosition.toInt()
    val settledPage = pagerState.settledPage
    val currentPage = pagerState.currentPage
    val pageOffset = pagerState.currentPageOffsetFraction

    val currentX = index * (dotSpacing + 2 * dotRadius)
    val targetX = currentPage * (dotSpacing + 2 * dotRadius) + pageOffset * (dotSpacing + 2 * dotRadius)

    val dotColor = if (index == currentPage) Color.Black else Color.Gray
    drawCircle(color = dotColor, radius = dotRadius, center = Offset(currentX, size.height - dotRadius))
   *//* val targetScale = 1.5f - 1f
    val scale = if (absPageOffset < .5) {
        1.0f + (absPageOffset * 2) * targetScale
    } else {
        1.5f + ((1 - (absPageOffset * 2)) * targetScale)
    }

    val factor = absPageOffset * PI
    val y = if (current >= settledPage) {
        -sin(factor) * 10f
    } else {
        sin(factor) * distance / 2
    }

    val dotColor = if (pagerState.currentPage == index) Color.Black else Color.Gray
    drawCircle(color = dotColor, radius = 6.dp.toPx() * scale, center = center.copy(y = center.y + y.toFloat()))*//*
}*/

