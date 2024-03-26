package com.example.stocki.feeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocki.market.MarketState
import com.example.stocki.market.MarketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor( val feedsUseCase: FeedsUseCase)  : ViewModel() {


    private val _state = MutableStateFlow<FeedStates>(FeedStates.Loading)
    val state: StateFlow<FeedStates> = _state
    fun fetchData() {
        viewModelScope.launch {
            _state.value = FeedStates.Loading
            try {
                val result = feedsUseCase()
                _state.value = result
            } catch (e: Exception) {
                _state.value = FeedStates.Error(e.message ?: "An error occurred")
            }
        }
    }
}