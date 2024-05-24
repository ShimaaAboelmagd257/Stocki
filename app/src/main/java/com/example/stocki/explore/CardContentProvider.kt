package com.example.stocki.explore

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.example.stocki.NavigationRoute
import com.example.stocki.R

@Composable
fun getCardContentList(): List<CardContent> {
    return listOf(
        CardContent("What is stock market?", painterResource(id = R.drawable.marketexplore),1, NavigationRoute.ExploreMarket.route),
        CardContent("How to invest?", painterResource(id = R.drawable.buyorsell),3, NavigationRoute.ExploreTrade.route),
        CardContent("Market dividends", painterResource(id = R.drawable.dividends),6, NavigationRoute.ExploreDividends.route),
        CardContent("Stock Exchange", painterResource(id = R.drawable.img),4, NavigationRoute.ExploreExchange.route),
        CardContent("stock splits", painterResource(id = R.drawable.stocksplit),5, NavigationRoute.ExploreSplits.route),
        CardContent("Market types", painterResource(id = R.drawable.tradingmarkets),2, NavigationRoute.ExploreTypes.route),
        CardContent("Portfolio diversification", painterResource(id = R.drawable.stocki),7, NavigationRoute.ExploreTrade.route),
        CardContent("Understanding indices", painterResource(id = R.drawable.backo),8, NavigationRoute.ExploreTrade.route)
    )
}
