package com.example.stocki.account.signin

import android.util.Log
import com.example.stocki.data.repository.UserRepository
import com.example.stocki.data.sharedpreferences.SharedPreference
import com.example.stocki.utility.Constans
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class SigninUseCase @Inject constructor(private val userRepository: UserRepository ,val sharedPreference: SharedPreference) {


suspend fun signIn(intent: SigninIntent): SigninState {
        return withContext(Dispatchers.IO) {
            if (!isValidInput(intent)) {
                return@withContext SigninState.Error("NotValidInput")
            }

            val userExists = userRepository.signIn(intent.email, intent.password)
            sharedPreference.addBoolean(Constans.SAVED_SIGNIN , true)
            Log.d("StockiSIU", "processSignin: ${  intent.email+ intent.password } " )
            return@withContext userExists
        }
    }



    private fun isValidInput(intent: SigninIntent): Boolean {
        return intent.email.isNotBlank() && intent.password.isNotBlank()
    }
}
