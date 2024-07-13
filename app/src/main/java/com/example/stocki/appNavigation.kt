package com.example.stocki

import android.annotation.SuppressLint
import android.app.Application
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.stocki.account.signin.SignInScreen
import com.example.stocki.account.signup.SignUpScreen
import com.example.stocki.entrance.EntranceScreen
import com.example.stocki.feeds.FeedsScreen
import dagger.hilt.android.HiltAndroidApp
import androidx.compose.runtime.*

import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.work.HiltWorkerFactory
import androidx.navigation.NavType

import androidx.navigation.navArgument
import com.example.stocki.data.sharedpreferences.SharedPreferences
import com.example.stocki.explore.*
import com.example.stocki.market.stocks.MarketsScreen
import com.example.stocki.market.bars.BarsViewModel
import com.example.stocki.market.stocksplits.SplitsView
import com.example.stocki.search.Searching
import com.example.stocki.splash.SplashScreen
import com.example.stocki.ticker.technicalIndicator.SMAView
import com.example.stocki.ticker.technicalIndicator.SmaViewModel
import com.example.stocki.ticker.tickerinfo.TickerInfoViewModel
import com.example.stocki.ticker.tickerinfo.tickerInfoView
import com.example.stocki.utility.Constans
import com.example.stocki.utility.NetworkState
import com.example.stocki.utility.NetworkViewModel
import javax.inject.Inject
import androidx.work.Configuration
import com.example.stocki.account.profile.ProfileView
import com.example.stocki.account.profile.UserPortfolio
import com.example.stocki.feeds.NewsItemCard
import com.example.stocki.holidays.HolidaysView
import com.example.stocki.market.dividends.DividendsView
import com.example.stocki.market.exchange.ExchangesView
import com.example.stocki.market.status.MarketStatusView
import com.example.stocki.search.search
import com.example.stocki.settings.CardGrid
import com.example.stocki.settings.sampleData
import com.example.stocki.splash.introItem


@HiltAndroidApp
class App: Application() ,Configuration.Provider{
    @Inject
    lateinit var workerFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()
}


val LocalSharedPreferences = compositionLocalOf<SharedPreferences> { error("No SharedPreferences found!") }
@Composable
fun MyApp() {
        val networkViewModel: NetworkViewModel = hiltViewModel()
        val context = LocalContext.current
        val toastMessage by networkViewModel.toastMessage.observeAsState()


        toastMessage?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

        MaterialTheme {
            Surface {
                MyAppContent(networkViewModel)
            }
        }

    }

@Composable
fun MyAppContent(networkViewModel: NetworkViewModel) {
    val networkState by networkViewModel.networkState.observeAsState(NetworkState.Unavailable)

    when (networkState) {
        is NetworkState.Available -> {
            NetworkAvailableContent()
        }
        is NetworkState.Unavailable -> {
           // NetworkUnavailableContent()
        }
    }
}

@Composable
fun NetworkUnavailableContent() {
}
@Composable
fun NetworkAvailableContent(){
      val configuration = LocalConfiguration.current
      //val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

      ProvideSharedPreferences {
          val sharedPreferences = LocalSharedPreferences.current
          val savedSignIn = sharedPreferences.getBoolean(Constans.SAVED_SIGNIN, false)
          val userId = sharedPreferences.getString("User_Id","User_Id")
          // val snackbarHostState = remember { SnackbarHostState() }
          val navController = rememberNavController()
          val context = LocalContext.current

          NavHost(navController, startDestination = NavigationRoute.Splash.route) {
              composable(NavigationRoute.Splash.route) {
                  /*SplashScreen(
                      onAnimationFinished = {

                          navController.navigate(NavigationRoute.Entrance.route) },
                      context = context,
                      navController = navController
                  )*/
                   SplashScreen(introItems = introItem, onButtonClick = {  if (savedSignIn) {
                       navController.navigate(NavigationRoute.Main.route)
                   } else {
                       navController.navigate(NavigationRoute.Entrance.route)
                   }})
              }

              composable(NavigationRoute.Entrance.route) {
                  EntranceScreen(
                      onSignInClick = { navController.navigate(NavigationRoute.SignIn.route) },
                      onSignUpClick = { navController.navigate(NavigationRoute.SignUp.route) },
                  )
              }

              composable(NavigationRoute.SignIn.route) {
                  SignInScreen(
                      navController = navController
                      //  snackbarHostState = snackbarHostState
                  )
              }

              composable(NavigationRoute.SignUp.route) {
                  SignUpScreen(navController = navController)
              }

              composable(NavigationRoute.Main.route) {
                  funi(userId)
              }

          }
      }
  }


@SuppressLint("SuspiciousIndentation")
@Composable
fun funi(userId:String) {

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
            Scaffold( bottomBar = { TabView(tabBarItems, navController)}, modifier = Modifier.background(
                Color.Transparent
            ), content = { it ->

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
                            MarketsScreen(
                                onTickerClicked = { ticker ->
                                navController.navigate("${NavigationRoute.TickerInfo.route}/$ticker") },
                                onSearchClicked = { navController.navigate(NavigationRoute.Searching.route)
                                })
                        }

                        composable(feedsTab.title) {
                            FeedsScreen(onItemClick = { newsId ->
                                navController.navigate("${NavigationRoute.FeedsItemInfo.route}/$newsId")
                            })
                        }

                        composable( route = "${NavigationRoute.FeedsItemInfo.route}/{newsId}",
                            arguments = listOf(navArgument("newsId") { type = NavType.StringType })
                        ){ backStackEntry ->
                            val newsId = backStackEntry.arguments?.getString("newsId")
                            newsId?.let { NewsItemCard(newsId) }

                        }
                        composable(searchTab.title) {
                          //  FeedsScreen()
                         //   WatchListView(onInsertClicked = { navController.navigate(NavigationRoute.Searching.route)
                          //  })
                            search(onSearchRequested = { navController.navigate(NavigationRoute.Searching.route) })
                        }
                        composable(moreTab.title) {
                            // tickerView()
                            // HolidaysView()
                            //  MarketStatusView()
                            //SplitsView()

                             CardGrid(cards = sampleData() , columns = 2 , onItemClick = { route ->
                                 navController.navigate(route)
                             })

                        }
                        composable(NavigationRoute.Splits.route){
                            SplitsView()
                        }

                        composable(NavigationRoute.MarketHoliday.route){
                            HolidaysView()
                        }
                        composable(NavigationRoute.MarketStatus.route){
                            MarketStatusView()
                        }
                        composable(NavigationRoute.MarketExchange.route){
                            ExchangesView()
                        }
                        composable(NavigationRoute.MarketDividends.route){
                            DividendsView()
                        }
                        composable(NavigationRoute.Profile.route){
                            ProfileView(userId = userId)
                        }
                        composable(NavigationRoute.Portfolio.route){
                            UserPortfolio(userId = userId)
                        }
                     /*   composable(
                            "${NavigationRoute.Splits.route}/{cardTitle}"

                        ) { backStackEntry ->
                            val cardTitle = backStackEntry.arguments?.getString("cardTitle")
                            val viewModel: SplitsViewmodel = hiltViewModel()
                            SplitsView(viewModel,cardTitle)
                        }*/
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
                            ExploreMarket()
                        }

                        composable(
                            "${NavigationRoute.ExploreDividends.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            ExploreDividends()
                        }
                        composable(
                            "${NavigationRoute.ExploreTypes.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            ExploreMarketTypes()
                        }

                        composable(
                            "${NavigationRoute.ExploreExchange.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            ExploreMarketExchange()
                        }
                        composable(
                            "${NavigationRoute.ExploreSplits.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            ExploreSplits()
                        }

                        composable(
                            "${NavigationRoute.ExploreTrade.route}/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            ExploreBuyAndSell()
                        }

                       /* composable(moreTab.title) {
                            val viewModel: SearchViewmodel = hiltViewModel()

                            search(viewmodel = viewModel,
                                onSearchRequested = { navController.navigate(NavigationRoute.Searching.route) })
                        }*/

                        composable(
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
                        }
                        composable(
                        route = "${NavigationRoute.TickerInfo.route}/{ticker}",
                        arguments = listOf(navArgument("ticker") { type = NavType.StringType })
                    ) { backStackEntry ->
                        val ticker = backStackEntry.arguments?.getString("ticker")
                        ticker?.let {
                            val viewModel: TickerInfoViewModel = hiltViewModel()
                            tickerInfoView(ticker, userId = userId,onTrading = { navController.navigate(NavigationRoute.Portfolio.route) })


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
    var selectedTabIndex by remember { mutableStateOf(0) }

    NavigationBar(containerColor = Color.Transparent) {
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
                },
                modifier = Modifier.fillMaxWidth(),
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





