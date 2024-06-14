package com.example.stocki.explore

import android.widget.GridLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.TopStart
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
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
            .padding(2.dp)) {
            repeat(4) { rowIndex ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(2) { columnIndex ->
                        val index = rowIndex * 2 + columnIndex
                        if (index < cardContentList.size) {
                            val content = cardContentList[index]
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                modifier = Modifier.padding(8.dp)
                                   .weight(1f)
                                    .clickable {
                                        onCardClicked(content.route, content.id)

                                    }
                            ) {
                                Column(


                                ) {
                                    Image(
                                        painter = content.image,
                                        contentDescription = null,
                                        modifier = Modifier
                                            .fillMaxWidth() .height(200.dp)
                                            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 5.dp))
                                            //.aspectRatio(1f) // Adjust aspect ratio as needed
                                        ,
                                         contentScale = ContentScale.Crop
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = content.title,
                                        color = Color.Black,
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.padding(16.dp).align(CenterHorizontally)
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
