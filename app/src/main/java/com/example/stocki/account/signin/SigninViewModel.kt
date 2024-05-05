package com.example.stocki.account.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocki.data.repository.UserRepository
import com.example.stocki.data.sharedpreferences.SharedPreference
import com.example.stocki.utility.Constans
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SigninViewModel @Inject constructor (
    private val signInUseCase: SigninUseCase) :ViewModel(){


    private val _viewState = MutableStateFlow<SigninState>(SigninState.Idle)
    val viewState: StateFlow<SigninState> = _viewState

    fun processSignin(intent: SigninIntent) {
    _viewState.value = SigninState.Loading

    viewModelScope.launch {
           try {
               val result = signInUseCase.signIn(intent)
               _viewState.value = result

           }  catch (e:Exception) {
               _viewState.value = SigninState.Error(e.message ?: "Unknown error")


           }
        Log.d("StockiSIVM", "processSignin: ${_viewState.value} ")
        }
    }

}