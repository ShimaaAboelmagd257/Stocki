package com.example.stocki.feeds

import android.util.Log
import com.example.stocki.data.repository.Repository
import com.example.stocki.utility.Constans.CACHE_EXPIRY_TIME
import com.example.stocki.utility.Constans.currentTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FeedsUseCase @Inject constructor( val repository: Repository) {

    suspend operator fun invoke(): FeedStates {
        return withContext(Dispatchers.IO) {
            val localSource = repository.getAllNews()
            val lastFetchTime = localSource.maxOfOrNull { it.fetched_at } ?: 0
            if (localSource.isNotEmpty() && currentTime - lastFetchTime < CACHE_EXPIRY_TIME) {
                Log.d("stockiFeedsUseCase", "localData  ${localSource.size}")
                FeedStates.Data(localSource)
            }else {
                val data = repository.fetchAndStoreTickerNews()
                Log.d("stockiFeedsUseCase", "remoteData  ${data.size}")
                FeedStates.Data(data)
            }
        }
    }

    suspend fun getNewsItemById(id:String):FeedStates{
        val data = repository.getNewsItemById(id)
        return FeedStates.newsItemInfo(data)
    }
}