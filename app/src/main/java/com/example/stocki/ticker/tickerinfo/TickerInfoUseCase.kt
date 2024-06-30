package com.example.stocki.ticker.tickerinfo

import android.annotation.SuppressLint
import android.util.Log
import com.example.stocki.data.pojos.BrandingSaved
import com.example.stocki.data.repository.Repository
import com.example.stocki.search.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickerInfoUseCase @Inject constructor(val repository: Repository) {

    @SuppressLint("SuspiciousIndentation")
    suspend operator fun invoke(ticker: String): TickerInfoState {
        return withContext(Dispatchers.IO) {
            try {
                val data = repository.getTickerInfo(ticker)
                val resultsList = listOf(data.results)
                if (resultsList.isNullOrEmpty()){
                    Log.d("TickerInfoUseCase", "resultsList.isNullOrEmpty() ${resultsList.isNullOrEmpty()}")

                }

            else{
                    val brandingList = resultsList.mapNotNull  {

                        BrandingSaved(
                            ticker =  it.ticker,
                            logoUrl = it.branding?.logo_url,
                            iconUrl = it.branding?.icon_url
                        )
                    }
                    repository.insertTickerLogo(brandingList)
            }


                TickerInfoState.Data(resultsList)
            } catch (e : Exception) {
                TickerInfoState.Error(e.message ?: "An error occurred")

            }

        }

    }
}

/*suspend  fun getAllTickerLogo( ): TickerInfoState {
       val data = repository.getAllTickerLogo()
       return TickerInfoState.Logo(data)
   }*/