package com.example.stocki.account.profile

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stocki.R
import com.example.stocki.data.pojos.account.PortfolioItem


@SuppressLint("SuspiciousIndentation")
@Composable
fun ProfileView(viewModel: ProfileViewmodel = hiltViewModel(), userId: String) {
    val state by viewModel.state.collectAsState()
    val portfolioState by viewModel.portfolioState.collectAsState()

    LaunchedEffect(userId) {
        viewModel.fetchUserData(userId)
        viewModel.fetchUserPortfolio(userId)
    }

    val items = listOf(
        painterResource(R.drawable.onedaylogo),
        painterResource(R.drawable.oneweeklogo),
        painterResource(R.drawable.onemonthlogo),
        painterResource(R.drawable.oneyearlogo),
    )
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            when (val profileState = state) {
                is ProfileState.Data -> {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        val profileInfo = profileState.user
                        Text(text = "Hello, " + profileInfo.name)
                        Text(text = profileInfo.email)
                        Spacer(modifier = Modifier.height(16.dp))
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .height(70.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(8.dp)
                                    .fillMaxSize(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "Your Balance")
                                Text(text = profileInfo.balance.toString())
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Achievements")
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            items(items.size) { index ->
                                CardItem(items[index])
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(text = "Investment Portfolio")
                    }
                }
                is ProfileState.Error -> {
                        Log.e("StockiProfileView", "Error in ProfileView ${profileState.error}")
                }
                ProfileState.Loading -> {

                        CircularProgressIndicator(modifier = Modifier.padding(150.dp, 300.dp))

                }
            }
        }

        item {
            when (val portfolioState = portfolioState) {
                is PortfolioState.Loading -> {
                    CircularProgressIndicator()
                }
                is PortfolioState.Data -> {
                    val portfolioItems = portfolioState.protfolio
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .height(400.dp) // Adjust height as needed
                    ) {
                        items(portfolioItems) { portfolioItem ->
                            PortfolioCardItem(portfolioItem)
                        }
                    }
                }
                is PortfolioState.Error -> {
                        Text(text = "Error: ${portfolioState.error}")
                }
            }
        }
    }
}

@Composable
fun PortfolioCardItem(portfolioItem: PortfolioItem) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = portfolioItem.ticker)
            Text(text = "${portfolioItem.averagePrice}")
        }
    }
}

@Composable
fun CardItem(img: Painter) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        Image(
            painter = img,
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .height(100.dp)
                .clip(RoundedCornerShape(10.dp)),
            contentScale = ContentScale.FillBounds
        )
    }
}