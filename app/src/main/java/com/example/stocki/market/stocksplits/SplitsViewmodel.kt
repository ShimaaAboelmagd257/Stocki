package com.example.stocki.market.stocksplits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SplitsViewmodel @Inject constructor(val splitsUsecase: SplitsUsecase)  : ViewModel() {
    private val _state = MutableStateFlow<SplitsState>(SplitsState.Loading)
    val state: StateFlow<SplitsState> = _state
    fun fetchData() {
        viewModelScope.launch {
            _state.value = SplitsState.Loading
            try {
                val result = splitsUsecase()
                _state.value = result
            } catch (e: Exception) {
                _state.value = SplitsState.Error(e.message ?: "An error occurred")
            }
        }
    }
}