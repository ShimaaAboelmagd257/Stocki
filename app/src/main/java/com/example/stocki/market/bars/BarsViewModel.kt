package com.example.stocki.market.bars

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class BarsViewModel @Inject constructor(val barsUseCase: BarsUseCase)  : ViewModel() {


    private val _state = MutableStateFlow<BarStates>(BarStates.Loading)
    val state: StateFlow<BarStates> = _state
    fun fetchData( stocksTicker: String,
                   multiplier: Int,
                   timespan: String,
                   from: String,
                   to: String) {
        viewModelScope.launch {
         //   _state.value = BarStates.Loading
            try {
                val result = barsUseCase(stocksTicker,multiplier,timespan,from,to)
                _state.value = result
            } catch (e: Exception) {
              //  _state.value = BarStates.Error(e.message ?: "An error occurred")
            }
        }
    }
}
