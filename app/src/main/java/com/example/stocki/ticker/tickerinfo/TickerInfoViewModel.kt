package com.example.stocki.ticker.tickerinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocki.data.firebase.tradingResult
import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.account.PortfolioItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class TickerInfoViewModel @Inject constructor(val tickerInfoUseCase: TickerInfoUseCase)  : ViewModel() {

    private val _state = MutableStateFlow<TickerInfoState>(TickerInfoState.Loading)
    val state: StateFlow<TickerInfoState> = _state

    private val _tradeState = MutableStateFlow<TradeState>(TradeState.Idle)
    val tradeState: StateFlow<TradeState> = _tradeState
    fun fetchTickerData(ticker: String) {
        viewModelScope.launch {
            _state.value = TickerInfoState.Loading
            try {
                val aggregateData = tickerInfoUseCase.getTickerItemById(ticker)
                val companies = tickerInfoUseCase(ticker)
                _state.value = TickerInfoState.CombinedData(companies, aggregateData)
            } catch (e: Exception) {
                _state.value = TickerInfoState.Error(e.message ?: "Error fetching data")
            }
        }
    }
/*    fun fetchData(ticker: String ) {
        viewModelScope.launch {
            Log.d("StockiTickerInfoViewModel", "fetchData ${ticker} ${_state.value}")

            _state.value = TickerInfoState.Loading
            try {
                val result = tickerInfoUseCase(ticker)
                _state.value = result.
                Log.d("StockiTickerInfoViewModel", "fetchData ${ticker} ${_state.value}")


            } catch (e: Exception) {
                Log.d("StockiTickerInfoViewModel", "fetchData ${ticker} ${_state.value}")

                _state.value = TickerInfoState.Error(e.message ?: "An error occurred")
            }
        }
    }*/
    fun fetchTickerItemById(ticker: String) {
        viewModelScope.launch {
            _state.value = TickerInfoState.Loading
            try {
                val tickerItem = tickerInfoUseCase.getTickerItemById(ticker)
                _state.value = TickerInfoState.Aggriigate(tickerItem)
            } catch (e: Exception) {
                _state.value = TickerInfoState.Error(e.message ?: "Error fetching ticker item")
            }
        }
    }


    fun buyStock(userId: String, stock: PortfolioItem) {
        viewModelScope.launch {
            _tradeState.value = TradeState.Loading
            val result = tickerInfoUseCase.buyStock(userId,stock)
            _tradeState.value = when (result) {
                is tradingResult.Success -> TradeState.Success
                is tradingResult.Error -> TradeState.Error(result.message)
            }
        }
    }

    fun sellStock(userId: String, ticker: String,quantity:Int,sellingPrice:Double) {
        viewModelScope.launch {
            _tradeState.value = TradeState.Loading
            val result = tickerInfoUseCase.sellStock(userId,ticker,quantity,sellingPrice )
            _tradeState.value = when (result) {
                is tradingResult.Success -> TradeState.Success
                is tradingResult.Error -> TradeState.Error(result.message)
            }
        }
    }
}

/*fun fetchTickerLogo() {
    viewModelScope.launch {

        _state.value = TickerInfoState.Loading
        try {
            val result = tickerInfoUseCase.getAllTickerLogo()
            _state.value = result
        } catch (e: Exception) {
            _state.value = TickerInfoState.Error(e.message ?: "An error occurred")
        }
    }
}*/

