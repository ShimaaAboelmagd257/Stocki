package com.example.stocki.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.stocki.NavigationRoute
import com.example.stocki.R

data class CardContent (val title:String, val size : Dp, val image: Painter, val route:String)

@Composable
fun sampleData():List<CardContent>  {
    val sampleData = listOf(
        CardContent("Stock Splits", 200.dp, painterResource(R.drawable.splitlogo), NavigationRoute.Splits.route),
        CardContent("Stock Status", 250.dp , painterResource(R.drawable.statuslogo), NavigationRoute.MarketStatus.route),
        CardContent("Daily OpenClose", 250.dp, painterResource(R.drawable.dologo), NavigationRoute.MarketDailyOC.route),
        CardContent("Holidays", 150.dp, painterResource(R.drawable.holilogo), NavigationRoute.MarketHoliday.route),
        CardContent("Exchange", 220.dp, painterResource(R.drawable.exchangelogo), NavigationRoute.MarketExchange.route),
        CardContent("Dividends", 200.dp, painterResource(R.drawable.divlogo), NavigationRoute.MarketDividends.route),
        CardContent("Profile", 210.dp, painterResource(R.drawable.proflog), NavigationRoute.Profile.route)

//#4C102A
    )
    return sampleData

}
