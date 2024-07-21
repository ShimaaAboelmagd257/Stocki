package com.example.stocki.watchlists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocki.data.pojos.AggregateData
import com.example.stocki.data.pojos.TickerTypes
import com.example.stocki.data.pojos.WatchList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(val watchListUseCase: WatchListsUseCase) : ViewModel() {


    private val _state = MutableStateFlow<WatchListsStates>(WatchListsStates.Loading)
    val watchListState: StateFlow<WatchListsStates> = _state

    fun fetchData() {
        viewModelScope.launch {
            _state.value = WatchListsStates.Loading
            try {
                val result = watchListUseCase.getAllWatchList()
                _state.value = result
            } catch (e: Exception) {
                _state.value = WatchListsStates.Error(e.message ?: "An error occurred")
            }
        }
    }
    fun removeTicker(ticker: WatchList) {
       viewModelScope.launch {
           watchListUseCase.deleteWatchTicker(ticker)
           _state.value = WatchListsStates.Deleting("deleted")
       }    }
    fun insertData(watchList: WatchList) {
        viewModelScope.launch {
             watchListUseCase.insertWatchList(watchList)
            _state.value = WatchListsStates.Success("success")
        }
    }

}
