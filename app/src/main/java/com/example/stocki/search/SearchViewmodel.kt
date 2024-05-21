package com.example.stocki.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewmodel @Inject constructor(val searchUsecase: SearchUsecase )  : ViewModel() {
    private val _state = MutableStateFlow<SearchState>(SearchState.Loading)
    val state: StateFlow<SearchState> = _state
    fun fetchData(markets: List<String>) {
            viewModelScope.launch {
                _state.value = SearchState.Loading
                try {
                    val result = searchUsecase(markets)
                    _state.value = result
                } catch (e: Exception) {
                    _state.value = SearchState.Error(e.message ?: "An error occurred")
                }
            }
        }

fun fetchAllData() {
    viewModelScope.launch {
        _state.value = SearchState.Loading
        try {
            val result = searchUsecase.getAllTickers()
            _state.value = result
        } catch (e: Exception) {
            _state.value = SearchState.Error(e.message ?: "An error occurred")
        }
    }
}
}


/*  fun fetchData(
        //ticker: String,
        market: List<String>
        // type:String,
        *//* market: String ,exchange: String ,cusip: String ,cik: String,date: String
                  ,search: String,sort: String)*//*
    ) */