package com.example.stocki.feeds

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stocki.data.pojos.NewsItem
import androidx.compose.foundation.Image
import androidx.compose.runtime.remember
import coil.compose.rememberImagePainter
import java.util.Collections.emptyList
import coil.transform.CircleCropTransformation
import com.example.stocki.utility.Constans.DEFAULT_IMAGE_URL
import com.example.stocki.utility.Constans.ReadMoreText
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.RectangleShape
import com.example.stocki.utility.Constans


@Composable
fun FeedsScreen(viewModel: FeedsViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchData() // Fetch data automatically when the screen is displayed
     //   Log.d("StockiMain", "getGroupedDailyBars ${viewModel.fetchData("2024-03-01")}")

    }

    when (val feedState = state) {
        is FeedStates.Loading -> {
            // Display loading indicator
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }
        is FeedStates.Data -> {
            val feedList = feedState.data ?: emptyList()
            LazyColumn {
                items(feedList.size) { newsItem ->
                    val newsData = feedList[newsItem]
                    NewsItemCard(newsItem = newsData)
                }
            }
        }

        is FeedStates.Error -> {
            // Display error message
            Text(feedState.error, modifier = Modifier.padding(16.dp))
        }
    }

    /*Button(onClick = { viewModel.fetchData("") }, modifier = Modifier.padding(16.dp)) {
        Text("Fetch Data")
    }*/
}
@SuppressLint("SuspiciousIndentation")
@Composable
fun NewsItemCard(newsItem: NewsItem) {
    var expandedText by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Title
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Image
            val imageUrl = newsItem.image_url ?: DEFAULT_IMAGE_URL
            Constans.LoadNetworkImage(
                url = imageUrl,
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .clip(shape = RectangleShape)
            )
            // Description
            ReadMoreText(
                text = newsItem.description,
                maxLines = if (expandedText) Int.MAX_VALUE else 2,
                onReadMoreClicked = { expandedText = true }
            )
           /* Text(
                text = newsItem.description,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 8.dp)
            )*/
            // Author and Publisher
            Text(
                text = "By ${newsItem.author} - ${newsItem.publisher.name}",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Published Date
            Text(
                text = "Published on ${newsItem.published_utc}",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(bottom = 8.dp)
            )


            // Keywords
            Text(
                text = "Keywords: ${newsItem.keywords/*.joinToString()*/}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Tickers
            Text(
                text = "Tickers: ${newsItem.tickers/*.joinToString()*/}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            /*// AMP URL
            Text(
                text = "AMP URL: ${newsItem.amp_url}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 8.dp)
            )*/
            // Article URL
            Text(
                text = "Article URL: ${newsItem.article_url}",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            /*// Publisher details
            Text(
                text = "Publisher: ${newsItem.publisher.name}, Homepage: ${newsItem.publisher.homepage_url}",
                style = MaterialTheme.typography.body2
            )*/
        }
    }
}
/*@Composable
fun NewsItemCard(newsItem: NewsItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Title
            Text(
                text = newsItem.title,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Description
            Text(
                text = newsItem.description,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Author and Publisher
            Text(
                text = "By ${newsItem.author} - ${newsItem.publisher.name}",
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Published Date
            Text(
                text = "Published on ${newsItem.publishedUtc}",
                style = MaterialTheme.typography.caption
            )
        }
    }
}*/