package com.example.stocki.explore

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.stocki.utility.Constans
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.example.stocki.R

@Composable
fun ExploreMarket(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(text = "what is a stock market ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = stringResource(id = R.string.ExploreMarkets)
               , style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.stockMarketUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}
@Composable
fun ExploreDividends(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(100.dp)
        ) {
            Text(text = "what is a stock dividends ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = stringResource(R.string.Dividends),
                style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.dividensUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}
@Composable
fun ExploreMarketTypes(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(60.dp)
        ) {
            Text(text = "what is a  market types ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = stringResource(id = R.string.MarketTypes) ,
                style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.typesUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}
@Composable
fun ExploreSplits(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current, id: Int =5) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(60.dp)
        ) {
            Text(text = "what is a  market splits ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = stringResource(id = R.string.Splits)
                ,style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.splitsUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}
@Composable
fun ExploreBuyAndSell(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current, id: Int = 3) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(80.dp)
        ) {
            Text(text = "When to buy and sell  ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = stringResource(id = R.string.Trade)
               , style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.buy_sellUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}


@Composable
fun ExploreMarketExchange(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(80.dp)
        ) {
            Text(text = " market Exchange ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = stringResource(id = R.string.Exchange)

                ,style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.stockExchangeUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}

