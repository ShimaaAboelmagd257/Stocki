package com.example.stocki.feeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocki.data.pojos.NewsItem
import com.example.stocki.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor( val feedsUseCase: FeedsUseCase , val repository: Repository)  : ViewModel() {


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

    fun fetchNewsItemById(id :String) {
        viewModelScope.launch {
            _state.value = FeedStates.Loading
            try {
                val result = feedsUseCase.getNewsItemById(id)
                _state.value = result
            } catch (e: Exception) {
                _state.value = FeedStates.Error(e.message ?: "An error occurred")
            }
        }
    }
     fun getNewsItemById(id: String): Flow<NewsItem?> {
        return repository.getNewsItemById(id)
            .flowOn(Dispatchers.IO) // Perform the flow on IO dispatcher
            .catch { emit(null) }   // Handle any errors
    }
}