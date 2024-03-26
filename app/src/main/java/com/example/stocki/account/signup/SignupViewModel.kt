package com.example.stocki.account.signup

import android.content.Intent
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stocki.data.repository.UserRepository
import com.example.stocki.data.sharedpreferences.SharedPreference
import com.example.stocki.utility.Constans
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class SignupViewModel @Inject constructor(
   /* private val userRepository: UserRepository , val sharedPreference: SharedPreference*/
val signupUseCase: SignupUseCase , val signupWithGoogleUseCase: SignupWithGoogleUseCase
    ) : ViewModel() {

  //  private val signUpUseCase = SignupUseCase(userRepository)

    private val _viewState = MutableLiveData<SignupState>()
    val viewState: LiveData<SignupState> = _viewState


    private val _state = mutableStateOf<SignUpWithGoogleState>(SignUpWithGoogleState.Idle)
    val state: MutableState<SignUpWithGoogleState> = _state
    fun signInWithGoogle(activityResultLauncher: ActivityResultLauncher<Intent>) {
        viewModelScope.launch {
            _state.value = SignUpWithGoogleState.Loading
            val success = signupWithGoogleUseCase.signInWithGoogle(activityResultLauncher)
            if (success) {
                _state.value = SignUpWithGoogleState.Success(signupWithGoogleUseCase.getGoogleAccount(null))
            } else {
                _state.value = SignUpWithGoogleState.Error("Sign-in with Google failed")
            }
        }
    }

    fun handleSignInResult(data: Intent?) {
        viewModelScope.launch {
            _state.value = SignUpWithGoogleState.Loading
            val success = signupWithGoogleUseCase.signInWithGoogle(data)
            if (success) {
                _state.value = SignUpWithGoogleState.Success(signupWithGoogleUseCase.getGoogleAccount(data))
            } else {
                _state.value = SignUpWithGoogleState.Error("Sign-in with Google failed")
            }
        }
    }
     /*fun signInWithGoogle(activityResultLauncher: ActivityResultLauncher<Intent>) {
        _state.value = SignUpWithGoogleState.Loading
        viewModelScope.launch {
            val signInIntent = userRepository.getGoogleSignInIntent()
            activityResultLauncher.launch(signInIntent)
            Log.d("StockiSUVM", "signInWithGoogle: " )

        }
    }


     fun handleSignInResult(data: Intent?) {
        viewModelScope.launch {
            val success = userRepository.handleGoogleSignInResult( data)
            if (success) {
                _state.value = SignUpWithGoogleState.Success(userRepository.getGoogleAcount(data))
             //   sharedPreference.addBoolean(Constans.SAVED_SIGNIN,true)
            } else {
                _state.value = SignUpWithGoogleState.Error("Sign-in with Google failed")
            }
            Log.d("StockiSUVM", "handleSignInResult: ${ _state.value} " )

        }
    }
*/
    fun processSignUp(intent: SignupIntent) {
        _viewState.value = SignupState.Loading
        viewModelScope.launch {
            try {
                val result = signupUseCase.signupWithEmail(intent)
                _viewState.value =
                    if (result) SignupState.Success else SignupState.Error("Failed to sign up")
            } catch (e: Exception) {
                _viewState.value = SignupState.Error(e.message ?: "Unknown error")
            }
            Log.d("StockiSUVM", "processSignUp: ${  _viewState.value } " )

        }
    }

}
/* private fun signInWithGoogle(activityResultLauncher: ActivityResultLauncher<Intent>) {
      _state.value = SignUpWithGoogleState.Loading
      viewModelScope.launch {
          val signInIntent = userRepository.getGoogleSignInIntent(activityResultLauncher.)
          activityResultLauncher.launch(signInIntent)
      }
  }*/
