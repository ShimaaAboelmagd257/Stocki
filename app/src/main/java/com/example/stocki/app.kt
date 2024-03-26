package com.example.stocki

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.stocki.search.search
import com.example.stocki.ticker.tickerinfo.TickerInfoViewModel
import com.example.stocki.ticker.tickerinfo.tickerInfoView

@HiltAndroidApp
class App: Application()


@Composable
fun MyApp() {


    val navController = rememberNavController()
    NavHost(navController, startDestination = NavigationRoute.Entrance.route) {
        // Define your composable destinations here
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
      
        composable(NavigationRoute.Main.route){
            funi()
        }
    }
    }




    // setting up the individual tabs
    @SuppressLint("SuspiciousIndentation")
   // @Preview
    @Composable
    fun funi(){
    val homeTab = TabBarItem(title = "Home", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    val alertsTab = TabBarItem(title = "Alerts", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    val settingsTab = TabBarItem(title = "Settings", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)
    val moreTab = TabBarItem(title = "More", selectedIcon = Icons.Filled.Home, unselectedIcon = Icons.Outlined.Home)

    val tabBarItems = listOf(homeTab, alertsTab, settingsTab, moreTab)
        val navController = rememberNavController()

        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(bottomBar = { TabView(tabBarItems, navController) }) {
                NavHost(
                    navController = navController,
                    startDestination  = homeTab.title,
                )                {


                    composable(homeTab.title) {
                       /* MarketsScreen(navController = navController, onTickerClicked = { ticker ->
                            navController.navigate("${NavigationRoute.TICKER_INFO.route}/$ticker")
                        })*/

                    }
                    composable(alertsTab.title) {
                       // FeedsScreen()
                    }
                    composable(settingsTab.title) {
                        //tickerView()
                    }
                    composable(moreTab.title) {
                       search(navController)
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
                }
            }
        }
    }

@Composable
fun TabView(tabBarItems: List<TabBarItem>, navController: NavController) {
    var selectedTabIndex by remember {
        mutableStateOf(0)
    }

    NavigationBar {
        // looping over each tab to generate the views and navigation for each item
        tabBarItems.forEachIndexed { index, tabBarItem ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    navController.navigate(tabBarItem.title)
                },
                icon = {
                    TabBarIconView(
                        isSelected = selectedTabIndex == index,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,

                    )
                },
                label = {Text(tabBarItem.title)})
        }
    }
}
@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title
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
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

sealed class NavigationRoute(val route: String) {
    object SignUp : NavigationRoute("signup")
    object SignIn : NavigationRoute("signin")
    object Entrance : NavigationRoute("entrance")
   // object Markets : NavigationRoute("markets")
    object Feeds : NavigationRoute("feeds")
    object Main:NavigationRoute("main")
    object TickerInfo: NavigationRoute("tickerInfo")
}












// end of the reusable components that can be copied over to any new projects
// ----------------------------------------

// This was added to demonstrate that we are infact changing views when we click a new tab
/*
@Composable
fun MoreView() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Thing 1")
        Text("Thing 2")
        Text("Thing 3")
        Text("Thing 4")
        Text("Thing 5")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
        MoreView()
}*/
/* @Composable
    fun MyApp(*//*sharedPreference: SharedPreference = hiltViewModel()*//*) {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = {
                BottomNavigation {
                    // Home screen
                    BottomNavigationItem(
                        selected = navController.currentDestination?.route == NavigationRoute.Main.route,
                        onClick = {
                            navController.navigate(NavigationRoute.Main.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                        label = { Text("Home") }
                    )

                    // News screen
                    BottomNavigationItem(
                        selected = navController.currentDestination?.route == NavigationRoute.NEWS.route,
                        onClick = {
                            navController.navigate(NavigationRoute.NEWS.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                            }
                        },
                        icon = { Icon(Icons.Filled.MailOutline, contentDescription = "News") },
                        label = { Text("News") }
                    )
                }
            }
        )
        {
        NavHost(navController, startDestination = NavigationRoute.Entrance.route) {
            composable(NavigationRoute.Entrance.route) {

                EntranceScreen(
                    onSignInClick = { navController.navigate(NavigationRoute.SignIn.route) },
                    onSignUpClick = { navController.navigate(NavigationRoute.SignUp.route) },
                )
            }
            composable(NavigationRoute.SignIn.route) {
                val viewModel: SigninViewModel = hiltViewModel()
                SignInScreen(signinViewModel = viewModel, navController = navController)
            }
            composable(NavigationRoute.SignUp.route) {
                val viewModel: SignupViewModel = hiltViewModel()
                SignUpScreen(signUpViewModel = viewModel)
            }
            composable(NavigationRoute.Main.route) {
                MainScreen() // Call your MainScreen composable here
            }
            composable(NavigationRoute.NEWS.route) {
                FeedsScreen()
            }
        }
    }
    }*/
/* @Composable
 fun MyApp(*//*sharedPreference: SharedPreference = hiltViewModel()*//*) {
       val navController = rememberNavController()

       NavHost(navController, startDestination = NavigationRoute.Entrance.route) {
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
                  // onSignInSuccess = { navController.navigate(NavigationRoute.Main.route) }
               )
           }
           composable(NavigationRoute.SignUp.route) {
               val viewModel: SignupViewModel = hiltViewModel()
               SignUpScreen(
                   signUpViewModel = viewModel,
                  // onSignUpSuccess = { navController.navigate(NavigationRoute.Main.route) }
               )
           }
           composable(NavigationRoute.Markets.route) {
               MarketsScreen() // Call your MainScreen composable here
           }
           composable(NavigationRoute.Feeds.route) {
               FeedsScreen()
           }

           composable(NavigationRoute.Main.route) {
               Scaffold(
                   bottomBar = {
                       BottomNavigation {
                           // Home screen
                           BottomNavigationItem(
                               selected = navController.currentDestination?.route == NavigationRoute.Feeds.route,
                               onClick = {
                                   navController.navigate(NavigationRoute.Feeds.route) {
                                       popUpTo(navController.graph.findStartDestination().id) {
                                           saveState = true
                                       }
                                       launchSingleTop = true
                                   }
                               },
                               icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
                               label = { Text("Home") }
                           )

                           BottomNavigationItem(
                               selected = navController.currentDestination?.route == NavigationRoute.Markets.route,
                               onClick = {
                                   // No need to navigate to Main screen if already on Main screen
                                   if (navController.currentDestination?.route != NavigationRoute.Markets.route) {
                                       navController.navigate(NavigationRoute.Markets.route) {
                                           popUpTo(navController.graph.findStartDestination().id) {
                                               saveState = true
                                           }
                                           launchSingleTop = true
                                       }
                                   }
                               },
                               icon = {  StockMarketIcon(
                                   modifier = Modifier.size(24.dp),
                                   color = if (navController.currentDestination?.route == NavigationRoute.Markets.route) {
                                       Color.Red // Change the color based on selection
                                   } else {
                                       Color.Gray // Change the color based on deselection
                                   }
                               ) },
                               label = { Text("Markets") }
                           )
                       }
                   }
               )

               {  MarketsScreen() // Call your MainScreen composable here
                   FeedsScreen()
               }
           }
       }
   }*/
/*
// Define a function to navigate to the Feeds screen
   val navigateToFeeds: () -> Unit = {
       navController.navigate(NavigationRoute.Feeds.route) {
           popUpTo(navController.graph.findStartDestination().id) {
               saveState = true
           }
           launchSingleTop = true
       }
   }

// Define a function to navigate to the Markets screen
val navigateToMarkets: () -> Unit = {
    if (navController.currentDestination?.route != NavigationRoute.Markets.route) {
        navController.navigate(NavigationRoute.Markets.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
        }
    }
}
*/

// Scaffold with BottomNavigation only for Markets and Feeds screens

// Call NavHost inside the Scaffold
/*composable(NavigationRoute.SignIn.route) {
         val viewModel: SigninViewModel = hiltViewModel()
         SignInScreen(
             signinViewModel = viewModel,
             navController = navController,
             // onSignInSuccess = { navController.navigate(NavigationRoute.Main.route) }
         )
     }*/
/*
@Composable
fun TabBarItem(
    title: String,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    badgeAmount: Int? = null
): TabBarItem {
    return TabBarItem(title, selectedIcon, unselectedIcon)
}
*/