package com.example.stocki

sealed class NavigationRoute(val route: String) {
    object SignUp : NavigationRoute("signup")
    object SignIn : NavigationRoute("signin")
    object Entrance : NavigationRoute("entrance")
    // object Markets : NavigationRoute("markets")
    object Feeds : NavigationRoute("feeds")
    object FeedsItemInfo :NavigationRoute("feedsInfo")
    object  FeedsList :NavigationRoute("feedsList")
    object Profile:NavigationRoute("profile")
    object Portfolio:NavigationRoute("portfolio")


    object EXplore:NavigationRoute("explore")
    object ExploreMarket :NavigationRoute("eplore")
    object ExploreTypes :NavigationRoute("types")
    object ExploreDividends :NavigationRoute("dividends")
    object ExploreSplits :NavigationRoute("splits")
    object ExploreTrade :NavigationRoute("trade")
    object ExploreExchange :NavigationRoute("exchange")

    object Main:NavigationRoute("main")
    object Splits:NavigationRoute("stockSplits")
    object Searching:NavigationRoute("searching")
    object TickerInfo: NavigationRoute("tickerInfo")
    object MarketHoliday:NavigationRoute("marketHoliday")
    object MarketStatus:NavigationRoute("marketStatus")
    object MarketDailyOC:NavigationRoute("marketOc")
    object MarketExchange:NavigationRoute("marketExchange")
    object MarketDividends:NavigationRoute("marketDividends")


    object TickerSMA : NavigationRoute("sma")
    object Splash : NavigationRoute("splash")

}
