package com.example.stocki.settings

import android.util.Pair
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.common.util.CollectionUtils.listOf

@Composable
fun CustomCard(
    modifier: Modifier =Modifier,
    content : @Composable () -> Unit
) {
    Card(
        modifier = modifier.padding(8.dp),
        elevation = 4.dp,
        shape = MaterialTheme.shapes.medium
    ){
        content()
    }
}

@Composable
fun VerticalAlignedGrid(
    columns: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        // Measure the children
        val itemWidth = constraints.maxWidth / columns
        val itemConstraints = constraints.copy(maxWidth = itemWidth)

        // Measure each child with the column width constraint
        val placeables = measurables.map { measurable ->
            measurable.measure(itemConstraints)
        }

        // Determine the height of each column
        val columnHeights = IntArray(columns) { 0 }
        placeables.forEachIndexed { index, placeable ->
            val column = index % columns
            columnHeights[column] += placeable.height
        }
        // Calculate the height of the layout based on the tallest column
        val height = columnHeights.maxOrNull() ?: constraints.minHeight

        layout(constraints.maxWidth, height) {
            // Track the Y offset for each column
            val columnYOffsets = IntArray(columns) { 0 }

            placeables.forEachIndexed { index, placeable ->
                val column = index % columns
                val x = column * itemWidth
                val y = columnYOffsets[column]
                // Place the item in the calculated position
                placeable.placeRelative(x, y)

                // Update the Y offset for the column
                columnYOffsets[column] += placeable.height
            }
        }
    }
}

@Composable
fun CardGrid(cards: List<Triple<String, Int,String>>, columns: Int ,onItemClick: (String) -> Unit) {
    VerticalAlignedGrid(columns = columns, modifier = Modifier.padding(16.dp)) {
        cards.forEach { card ->
            CustomCard(
                modifier = Modifier.height(card.second.dp).clickable {
                    onItemClick(card.third)
                }


            ) {
                Box(Modifier.fillMaxSize().padding(16.dp)) {
                 Text(text = card.first)
                }
            }
        }
    }
}

@Composable
fun PreviewCardGrid() {
    val sampleData = listOf(
        Pair("Stock Splits", 150),
        Pair("Stock Status", 250),
        Pair("Daily OpenClose", 250),
        Pair("Holidays", 150),
        Pair("Exchange", 220),
        Pair("Dividends", 200),
        Pair("Profile", 210)


    )

  //  CardGrid(cards = sampleData , columns = 2 , onItemClick = )
}

@Preview(showBackground = true)
@Composable
fun PreviewCardGridPreview() {
    PreviewCardGrid()
}