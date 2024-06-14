package com.example.stocki.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import com.bumptech.glide.request.transition.Transition
import androidx.core.app.NotificationCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.example.stocki.MainActivity
import com.example.stocki.R
import com.example.stocki.data.repository.Repository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import android.os.Handler
import android.os.Looper
import com.example.stocki.data.pojos.NewsItem

@HiltWorker
class feedsWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: Repository
) : CoroutineWorker(context, workerParams) {


    override suspend fun doWork(): Result {

        Log.d("stockiFeedsWorker", "doWork started")
        return try {
            createNotificationChannel()
            val newsDetails = repository.getAllnewsTitles()
            Log.d("stockiFeedsWorker", "Fetched news details: ${newsDetails.size} items")
            showNotificationsWithDelay(newsDetails)


            Result.success()
        } catch (e: Exception) {
            Log.e("stockiFeedsWorker", "Error in doWork", e)
            Result.failure()
        }
    }



    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "News Channel"
            val descriptionText = "Channel for news notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("news_channel", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
            Log.d("stockiMainActivty","createNotificationChannel" )

        }
    }
    private fun showNotificationsWithDelay(newsItems: List<NewsItem>) {
        val handler = Handler(Looper.getMainLooper())
        val interval = 60000L // 1 minute in milliseconds

        for ((index, newsItem) in newsItems.withIndex()) {
            val delay = index * interval
            handler.postDelayed({
                showNotification(newsItem)
            }, delay)
        }
    }
   private fun showNotification(newsItem: NewsItem) {
       val title = newsItem.title
       val message = newsItem.title
       val imageUrl = newsItem.image_url


       val intent = Intent(applicationContext, MainActivity::class.java).apply {
           putExtra("news_details", message)
       }
       val pendingIntent: PendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)


       Glide.with(applicationContext)
           .asBitmap()
           .load(imageUrl)
           .into(object : CustomTarget<Bitmap>() {
               override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {

                   val notificationBuilder = NotificationCompat.Builder(applicationContext, "news_channel")
                       .setContentTitle(title)
                       .setContentText(message)
                       .setLargeIcon(resource)
                       .setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                       .setContentIntent(pendingIntent)
                       .setAutoCancel(true)
                       .setSmallIcon(R.drawable.log)
                       .build()

                   val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                   notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder) // Use a unique notification ID

               }

               override fun onLoadCleared(placeholder: Drawable?) {
                   // Handle case when image loading is cleared
               }

               override fun onLoadFailed(errorDrawable: Drawable?) {
                   super.onLoadFailed(errorDrawable)
                   val notificationBuilder = NotificationCompat.Builder(applicationContext, "news_channel")
                       .setContentTitle(title)
                       .setContentText(message)
                       .setSmallIcon(R.drawable.log)
                       .setContentIntent(pendingIntent)
                       .setAutoCancel(true)
                       .build()

                   val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                   notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder) // Use a unique notification ID
               }
           })
   }
}
