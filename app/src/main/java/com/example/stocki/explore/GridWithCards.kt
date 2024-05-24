package com.example.stocki.explore

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun GridWithCards(onCardClicked: (route: String,it :Int) -> Unit) {
    val scrollState = rememberScrollState()

    val cardContentList = getCardContentList()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)) {
            repeat(4) { rowIndex ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(2) { columnIndex ->
                        val index = rowIndex * 2 + columnIndex
                        if (index < cardContentList.size) {
                            val content = cardContentList[index]
                            Card(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .weight(1f)
                                    .clickable {
                                        onCardClicked(content.route, content.id)

                                    }
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(16.dp)

                                ) {
                                    Text(
                                        text = content.title,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.body1
                                    )
                                    Image(
                                        painter = content.image,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .padding(vertical = 20.dp)
                                            .size(200.dp)
                                            .padding(16.dp)
                                        ,
                                        // contentScale = ContentScale.Crop
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
