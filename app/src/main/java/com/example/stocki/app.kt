package com.example.stocki

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stocki.account.signin.SignInScreen
import com.example.stocki.account.signin.SigninViewModel
import com.example.stocki.account.signup.SignUpScreen
import com.example.stocki.account.signup.SignupViewModel
import com.example.stocki.entrance.EntranceScreen
import com.example.stocki.feeds.FeedsScreen
import dagger.hilt.android.HiltAndroidApp
import androidx.compose.runtime.*

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.stocki.data.sharedpreferences.SharedPreferences
import com.example.stocki.explore.*
import com.example.stocki.market.stocks.MarketsScreen
import com.example.stocki.market.bars.BarsViewModel
import com.example.stocki.market.stocksplits.SplitsView
import com.example.stocki.search.SearchViewmodel
import com.example.stocki.search.Searching
import com.example.stocki.search.search
import com.example.stocki.splash.SplashScreen
import com.example.stocki.ticker.technicalIndicator.LineChartExample
import com.example.stocki.ticker.technicalIndicator.SMAView
import com.example.stocki.ticker.technicalIndicator.SmaViewModel
import com.example.stocki.ticker.tickerinfo.TickerInfoViewModel
import com.example.stocki.ticker.tickerinfo.tickerInfoView
import com.example.stocki.utility.Constans

@HiltAndroidApp
class App: Application()

val LocalSharedPreferences = compositionLocalOf<SharedPreferences> { error("No SharedPreferences found!") }

/*
@Composable
fun PortraitContent() {

}

@Composable
fun LandscapeContent() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationRoute.TickerSMA.route) {

    }
    }
*/
    @Composable
    fun MyApp() {
        val configuration = LocalConfiguration.current
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        ProvideSharedPreferences {
            val sharedPreferences = LocalSharedPreferences.current
            val savedSignIn = sharedPreferences.getBoolean(Constans.SAVED_SIGNIN, false)
            // val snackbarHostState = remember { SnackbarHostState() }
            val navController = rememberNavController()
            val context = LocalContext.current

            NavHost(navController, startDestination = NavigationRoute.Splash.route) {
                composable(NavigationRoute.Splash.route) {
                    SplashScreen(
                        onAnimationFinished = {

                            navController.navigate(NavigationRoute.Entrance.route) },
                        context = context,
                        navController = navController
                    )
                }
                composable(NavigationRoute.Entrance.route) {
                    EntranceScreen(
                        onSignInClick = { navController.navigate(NavigationRoute.SignIn.route) },
                        onSignUpClick = { navController.navigate(NavigationRoute.SignUp.route) },
                    )
                }

                composable(NavigationRoute.SignIn.route) {
                    val viewModel: SigninViewModel = hiltViewModel()
                    SignInScreen(
                        signinViewModel = viewModel,
                        navController = navController,
                        //  snackbarHostState = snackbarHostState
                    )
                }

                composable(NavigationRoute.SignUp.route) {
                    val viewModel: SignupViewModel = hiltViewModel()
                    SignUpScreen(
                        signUpViewModel = viewModel,
                    )
                }

                composable(NavigationRoute.Feeds.route) {
                    FeedsScreen()
                }
                composable(NavigationRoute.Splits.route) {
                    SplitsView()
                }

                composable(NavigationRoute.Main.route) {
                   /* MarketsScreen( onTickerClicked = { ticker ->
                        navController.navigate("${NavigationRoute.TickerSMA.route}/$ticker")
                    })*/
                    funi()
                }
                composable(
                    route = "${NavigationRoute.TickerSMA.route}/{ticker}",
                    arguments = listOf(navArgument("ticker") { type = NavType.StringType })
                ) { backStackEntry ->
                    val ticker = backStackEntry.arguments?.getString("ticker")
                    ticker?.let {
                        val viewModel: SmaViewModel = hiltViewModel()
                        val barsViewModel :BarsViewModel = hiltViewModel()
                        SMAView(ticker) {
                            viewModel.fetchData(ticker)
                            barsViewModel.fetchData(ticker,1,"day","2024-04-20","2024-04-30")
                        }

                    }
                }
                composable(
                    route = "${NavigationRoute.TickerInfo.route}/{ticker}",
                    arguments = listOf(navArgument("ticker") { type = NavType.StringType })
                ) { backStackEntry ->
                    val ticker = backStackEntry.arguments?.getString("ticker")
                    ticker?.let {
                        val viewModel: TickerInfoViewModel = hiltViewModel()
                        tickerInfoView(ticker) {
                            viewModel.fetchData(ticker)
                        }

                    }
                }
                /* composable(route = "${NavigationRoute.TickerSMA.route}/{ticker}",
                   arguments = listOf(navArgument("ticker") { type = NavType.StringType })){
                    backStackEntry ->
                val ticker = backStackEntry.arguments?.getString("ticker")
                ticker?.let {
                    val viewModel: SmaViewModel = hiltViewModel()
                    val barsViewModel :BarsViewModel = hiltViewModel()
                    SMAView(ticker) {
                        viewModel.fetchData(ticker*//*,"day"*//*)
                        barsViewModel.fetchData(ticker,1,"day","2024-03-26","2024-04-09")
                    }

                }

            }*/
            }
        }

    }



    // setting up the individual tabs
    @SuppressLint("SuspiciousIndentation")
   // @Preview
    @Composable
    fun funi() {

        val newsIcon = painterResource(R.drawable.newsunselected)
        val newsFilledIcon = painterResource(R.drawable.news)
        val marketIcon = painterResource(R.drawable.growth)
        val marketFilledIcon = painterResource(R.drawable.growthfilled)
        val searchIcon = painterResource(R.drawable.searchdollar)
        val searchFilledIcon = painterResource(R.drawable.searchfilledd)
        val bookIconFilled = painterResource(R.drawable.bookselected)
        val bookIcon = painterResource(R.drawable.bookunslected)
        val moreIconFilled = painterResource(R.drawable.morefilledicon)
        val moreIcon = painterResource(R.drawable.moreicon)

        val homeTab = TabBarItem(
            title = "Market",
            selectedIcon = marketFilledIcon,
            unselectedIcon = marketIcon
        )
        val feedsTab = TabBarItem(
            title = "Feeds",
            selectedIcon = newsFilledIcon,
            unselectedIcon = newsIcon
        )
        val searchTab = TabBarItem(
            title = "Search",
            selectedIcon = searchFilledIcon,
            unselectedIcon = searchIcon
        )
        val moreTab = TabBarItem(
            title = "More",
            selectedIcon = moreIconFilled,
            unselectedIcon = moreIcon
        )

        val explore = TabBarItem(
            title = "Explore",
            selectedIcon = bookIconFilled,
            unselectedIcon = bookIcon
        )

        val tabBarItems = listOf(homeTab, feedsTab,explore ,searchTab, moreTab)
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold( bottomBar = { TabView(tabBarItems, navController)}, content = { it ->

               Box( modifier = Modifier.padding(it)){
                    NavHost(
                        navController = navController,
                        startDestination = homeTab.title,
                    ) {
                        composable(NavigationRoute.EXplore.route) {
                        GridWithCards { route, cardId ->
                            navController.navigate("$route/$cardId")
                        }
                    }
                        composable(homeTab.title) {
                            MarketsScreen(onTickerClicked = { ticker ->
                                navController.navigate("${NavigationRoute.TickerInfo.route}/$ticker")
                            })
                        }
                        composable(feedsTab.title) {
                            FeedsScreen()
                        }
                        composable(searchTab.title) {
                            FeedsScreen()
                        }
                        composable(moreTab.title) {
                            // tickerView()
                            // HolidaysView()
                            //  MarketStatusView()
                            SplitsView()

                        }
                        composable(NavigationRoute.Searching.route) {
                            Searching()
                        }
                        /*composable(explore.title) {
                            GridWithCards(onCardClicked = { cardId ->

                                navController.navigate(

                                    "${NavigationRoute.ExploreMarket.route}/$cardId")
                            })
                        }*/
                        composable(
                            "${NavigationRoute.ExploreMarket.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val cardId = backStackEntry.arguments?.getInt("cardId") ?: 0
                            ExploreMarket()
                        }

                        composable(
                            "${NavigationRoute.ExploreDividends.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val cardId = backStackEntry.arguments?.getInt("cardId") ?: 0
                            ExploreDividends()
                        }
                        composable(
                            "${NavigationRoute.ExploreTypes.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val cardId = backStackEntry.arguments?.getInt("cardId") ?: 0
                            ExploreMarketTypes()
                        }

                        composable(
                            "${NavigationRoute.ExploreExchange.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val cardId = backStackEntry.arguments?.getInt("cardId") ?: 0
                            ExploreMarketExchange()
                        }
                        composable(
                            "${NavigationRoute.ExploreSplits.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val cardId = backStackEntry.arguments?.getInt("cardId") ?: 0
                            ExploreSplits(id = cardId)
                        }

                        composable(
                            "${NavigationRoute.ExploreTrade.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val cardId = backStackEntry.arguments?.getInt("cardId") ?: 0
                            ExploreBuyAndSell(id = cardId)
                        }




                        composable(moreTab.title) {
                            val viewModel: SearchViewmodel = hiltViewModel()

                            search(viewmodel = viewModel,
                                onSearchRequested = { navController.navigate(NavigationRoute.Searching.route) })
                        }

                       /* composable(
                            route = "${NavigationRoute.TickerSMA.route}/{ticker}",
                            arguments = listOf(navArgument("ticker") { type = NavType.StringType })
                        ) { backStackEntry ->
                            val ticker = backStackEntry.arguments?.getString("ticker")
                            ticker?.let {
                                val viewModel: SmaViewModel = hiltViewModel()
                                val barsViewModel: BarsViewModel = hiltViewModel()
                                SMAView(ticker) {
                                    viewModel.fetchData(ticker)
                                    barsViewModel.fetchData(
                                        ticker,
                                        1,
                                        "day",
                                        "2024-04-20",
                                        "2024-04-30"
                                    )
                                }

                            }
                        }*/
                        composable(
                        route = "${NavigationRoute.TickerInfo.route}/{ticker}",
                        arguments = listOf(navArgument("ticker") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val ticker = backStackEntry.arguments?.getString("ticker")
                        ticker?.let {
                            val viewModel: TickerInfoViewModel = hiltViewModel()
                            tickerInfoView(ticker) {
                                viewModel.fetchData(ticker)
                            }

                        }
                    }
                    }



            }
        }
            )
        }
    }
@Composable
fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }

    NavigationBar {
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.title)
                },
                icon ={
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title
                    )

                }
        },modifier = Modifier.size(100.dp),
                label = {
                    Text(tabBarItem.title)

                }
            )
        }
    }
}
@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: Painter,
    unselectedIcon: Painter,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            painter = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title,
            modifier = Modifier.size(30.dp)
        )
    }
}
@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}

data class TabBarItem(
    val selectedIcon: Painter,
    val unselectedIcon: Painter,
    val title: String
)

sealed class NavigationRoute(val route: String) {
    object SignUp : NavigationRoute("signup")
    object SignIn : NavigationRoute("signin")
    object Entrance : NavigationRoute("entrance")
   // object Markets : NavigationRoute("markets")
    object Feeds : NavigationRoute("feeds")


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
    object TickerSMA : NavigationRoute("sma")
    object Splash : NavigationRoute("splash")

}



@Composable
fun ProvideSharedPreferences(content: @Composable () -> Unit) {
    val context = LocalContext.current
    val sharedPreferences = remember {
        SharedPreferences(context)
    }
    CompositionLocalProvider(LocalSharedPreferences provides sharedPreferences) {
        content()
    }
}





