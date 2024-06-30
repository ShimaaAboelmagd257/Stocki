package com.example.stocki

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import android.view.Menu
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.stocki.account.signup.SignUpScreen
import com.example.stocki.account.signup.SignupViewModel
import com.example.stocki.data.localDatabase.StockiDatabase
import com.example.stocki.databinding.ActivityMainBinding
import com.example.stocki.data.remoteDatabase.StockiClient
import com.example.stocki.notification.feedsWorker
import com.example.stocki.utility.Constans
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var db: StockiDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
     //   requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        super.onCreate(savedInstanceState)
        val configuration = resources.configuration
        val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        db = Room.databaseBuilder(
            applicationContext,
            StockiDatabase::class.java, "Stocki Database"
        )
            .fallbackToDestructiveMigration()
            .build()
        setContent{
            MyApp()
        }
       val periodicWorkRequest = PeriodicWorkRequestBuilder<feedsWorker>(1, TimeUnit.HOURS).build()

      WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "NewsWork",
            ExistingPeriodicWorkPolicy.KEEP,
           periodicWorkRequest
        )
        Log.d("stockiMainActivity", "WorkManager setup complete")

        WorkManager.getInstance(this).getWorkInfosForUniqueWorkLiveData("NewsWork")
            .observe(this, Observer { workInfos ->
                if (workInfos == null || workInfos.isEmpty()) {
                    Log.d("stockiMainActivity", "No work infos found")
                    return@Observer
                }
                for (workInfo in workInfos) {
                    Log.d("stockiMainActivity", "WorkInfo: ${workInfo.state}")
                }
            })
      /*  intent?.let {
            handleIntent(it)

        }

*/


    }

   /* override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let {
            handleIntent(it)
        }
    }

    private fun handleIntent(intent: Intent) {
        val newsDetails = intent.getStringExtra("news_details")
        // Pass the news details to the navigation component
        val navController = rememberNavController()
        navController.navigate("${NavigationRoute.Feeds.route}?news_details=$newsDetails")
    }*/




    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}