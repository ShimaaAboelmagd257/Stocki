package com.example.stocki

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.stocki.account.signup.SignUpScreen
import com.example.stocki.account.signup.SignupViewModel
import com.example.stocki.databinding.ActivityMainBinding
import com.example.stocki.data.remoteDatabase.StockiClient
import com.example.stocki.utility.Constans
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var stockiClient: StockiClient
    override fun onCreate(savedInstanceState: Bundle?) {
        //signupFactory = SignupFactory(UserRepository.getInstance(applicationContext))
        super.onCreate(savedInstanceState)
        setContent {
              MyApp()

        }
        stockiClient = StockiClient.getInstance()

        lifecycleScope.launch {
            try {
            /*    val obj3  = stockiClient.getGroupedDailyBars("2023-01-09")
                Log.d("StockiMain", "getGroupedDailyBars $obj3")

                val obj = stockiClient.getAggregateBars(
                    "AAPL",
                    1,
                    "day",
                    "2023-01-09",
                    "2023-01-09",
                )
                Log.d("StockiMain", "getAggregateBars $obj")*/
            /*    val obj2 = stockiClient.getDailyOpenClosePrices("AAPL","2023-01-09",Constans.Api_Key)
                val obj4 = stockiClient.getPreviousClose("AAPL" , Constans.Api_Key)
                val obj5 = stockiClient.getApiCondition(Constans.Api_Key)
               val obj6 = stockiClient.getDividends(Constans.Api_Key)
               val obj7 = stockiClient.getExchanges(Constans.Api_Key)
                val obj8 = stockiClient.getMarketHolidays(Constans.Api_Key)*/
               //val obj9 = stockiClient.getMarketStatus(Constans.Api_Key)
             //  val obj11 = stockiClient.getStockFinancial("APPL" , Constans.Api_Key)
             //   val obj12 = stockiClient.getStockSplits()
//              val obj13  = stockiClient.getTicker("stocks" )
                //val obj14 = stockiClient.getTickerEvents("META",Constans.Api_Key)
            //     val obj15 = stockiClient.getTickerInfo("FCF")
            //  val obj16 = stockiClient.getTickerNews(Constans.Api_Key)
          //  val obj17 = stockiClient.getTickerTypes("stocks","us", Constans.Api_Key)


             //
            /*    Log.d("stocki", "getDailyOpenClosePrices $obj2")
                Log.d("stocki", "getPreviousClose $obj4")
                Log.d("stocki", "getApiCondition $obj5")
                Log.d("stocki", "stockiClient $obj6")
                Log.d("stocki", "getExchanges $obj7")
                Log.d("stocki", "getMarketHolidays $obj8")*/
               // Log.d("stocki", "getMarketStatus $obj9")
               // Log.d("stocki", "getStockFinancial $obj11")
              //  Log.d("stocki", "getStockSplits $obj12")
              //  Log.d("stocki", "getTicker $obj13")
               // Log.d("stocki", "getTickerEvents $obj14")
            //   Log.d("stockiMAIN", "getTickerInfo $obj13")
                //Log.d("stocki", "getTickerNews $obj16")
       //   Log.d("stocki", "getTickerTypes $obj17")


            } catch (e: Exception) {
                Log.e("stocki", "Error: ${e.message}", e)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}