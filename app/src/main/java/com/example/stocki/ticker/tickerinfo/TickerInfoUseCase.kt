package com.example.stocki.ticker.tickerinfo

import android.annotation.SuppressLint
import android.util.Log
import com.example.stocki.data.firebase.tradingResult
import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.BrandingSaved
import com.example.stocki.data.pojos.Company
import com.example.stocki.data.pojos.account.PortfolioItem
import com.example.stocki.data.repository.Repository
import com.example.stocki.data.repository.UserRepository
import com.example.stocki.search.SearchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TickerInfoUseCase @Inject constructor(val repository: Repository , val userRepository: UserRepository) {

    @SuppressLint("SuspiciousIndentation")
    suspend operator fun invoke(ticker: String): List<Company> {
        return withContext(Dispatchers.IO) {
          //  try {
                val data = repository.getTickerInfo(ticker)
                val resultsList = listOf(data.results)
                if (resultsList.isNullOrEmpty()) {
                    Log.d(
                        "TickerInfoUseCase",
                        "resultsList.isNullOrEmpty() ${resultsList.isNullOrEmpty()}"
                    )

                } else {
                    val brandingList = resultsList.mapNotNull {

                        BrandingSaved(
                            ticker = it.ticker,
                            logoUrl = it.branding?.logo_url,
                            iconUrl = it.branding?.icon_url
                        )
                    }
                 //   repository.insertTickerLogo(brandingList)
                }


                //  TickerInfoState.Data(resultsList)
            resultsList
         /*   }catch (e:Exception){
                Company("","","","","","""""")
            }*/

        }
    }

        suspend fun getTickerItemById(T: String): AggregateData {
            // return withContext(Dispatchers.IO){
            return repository.getTickerItemById(T)
            // }
        }

        suspend fun sellStock(
            userId: String,
            ticker: String,
            quantity: Int,
            sellingPrice: Double
        ): tradingResult {
            return userRepository.sellStock(userId, ticker, quantity, sellingPrice)
        }

        suspend fun buyStock(userId: String, stock: PortfolioItem): tradingResult {
            return userRepository.buyStock(userId, stock)
        }

    }

/*suspend  fun getAllTickerLogo( ): TickerInfoState {
       val data = repository.getAllTickerLogo()
       return TickerInfoState.Logo(data)
   }*/