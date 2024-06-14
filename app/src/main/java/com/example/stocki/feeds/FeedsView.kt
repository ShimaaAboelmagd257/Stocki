package com.example.stocki.feeds

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.remember
import java.util.Collections.emptyList
import com.example.stocki.utility.Constans.DEFAULT_IMAGE_URL
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import com.example.stocki.data.pojos.NewsItem
import com.example.stocki.data.pojos.NewsItemResponse
import com.example.stocki.utility.Constans

@Composable
fun Feeds(newsItem: NewsItem, onItemClick: (String) -> Unit){

        Card(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
                .clickable { onItemClick(newsItem.id) },
            elevation = 10.dp,
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text =newsItem.tickers ,//.first(),
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = newsItem.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = newsItem.published_utc,
                        fontSize = 10.sp,
                        color = Color.Gray,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                val imageUrl = newsItem.image_url ?: DEFAULT_IMAGE_URL
                Constans.LoadNetworkImage(
                    url = imageUrl,
                    modifier = Modifier
                        .size(130.dp)
                        .clip(RoundedCornerShape(8.dp)),
                )
            }
        }

}

@Composable
fun FeedsScreen(viewModel: FeedsViewModel = hiltViewModel(), onItemClick: (String) -> Unit) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData()
    }

    when (val feedState = state) {
        is FeedStates.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is FeedStates.Data -> {
            val feedList = feedState.data ?: emptyList()
            LazyColumn {
                items(feedList.size) { newsItem ->
                    val newsData = feedList[newsItem]
                    Feeds(newsItem = newsData , onItemClick = onItemClick )
                }
            }
        }

        is FeedStates.Error -> {
              Log.e("stockiFeedsError","${feedState.error}")
        }
        is FeedStates.newsItemInfo -> TODO()
        //is FeedStates.LocalData -> TODO()
    }


}
@SuppressLint("SuspiciousIndentation")
@Composable
fun NewsItemCard(newsId: String,viewModel: FeedsViewModel = hiltViewModel()) {
    var expandedText by remember { mutableStateOf(false) }
    val newsItem by viewModel.getNewsItemById(newsId).collectAsState(initial = null)
    LaunchedEffect(newsId) {
        viewModel.fetchNewsItemById(newsId)
    }
    newsItem?.let { item ->
        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = item.title, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Constans.LoadNetworkImage(
                url = item.image_url,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = item.published_utc, fontSize = 9.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Describtion", fontSize = 16.sp, fontWeight = FontWeight.Bold ,color = Color.Gray)
            Text(text = item.description , fontSize = 12.sp, color = Color.Black)
            Log.e("stockiFeedsitemdescription","${ item.description}")

            Spacer(modifier = Modifier.height(40.dp))
            Text(text = "Bublished by ", fontSize = 12.sp, fontWeight = FontWeight.Bold ,color = Color.Gray)
            Text(text = item.author , fontSize = 12.sp, color = Color.Black)

        }
    } ?: run {
        CircularProgressIndicator(modifier = Modifier)
    }
}
