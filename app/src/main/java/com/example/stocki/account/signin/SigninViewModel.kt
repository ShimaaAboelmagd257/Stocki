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
    /*private val userRepository: UserRepository , val sharedPreference: SharedPreference*/
    private val signInUseCase: SigninUseCase) :ViewModel(){


   // private val signipUseCase = SigninUseCase(userRepository , sharedPreference)
    private val _viewState = MutableStateFlow<SigninState>(SigninState.Idle)
    val viewState: StateFlow<SigninState> = _viewState

    /*private val _viewState = MutableLiveData<SigninState>()
    val viewState: LiveData<SigninState> get() = _viewState*/


    fun processSignin(intent: SigninIntent) {
    _viewState.value = SigninState.Loading

    viewModelScope.launch {
           try {
               val result = signInUseCase.signIn(intent)
               _viewState.value = if (result) SigninState.Success else SigninState.Error("Failed to sign in")
             //  if (result) sharedPreference.addBoolean(Constans.SAVED_SIGNIN,true)

           }  catch (e:Exception) {
               _viewState.value = SigninState.Error(e.message ?: "Unknown error")


           }
        Log.d("StockiSIVM", "processSignin: ${_viewState.value} ")
        }
    }

}