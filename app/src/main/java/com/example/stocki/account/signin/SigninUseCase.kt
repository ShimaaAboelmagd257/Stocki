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


suspend fun signIn(intent: SigninIntent): Boolean {
        return withContext(Dispatchers.IO) {
            // Validate user input
            if (!isValidInput(intent)) {
                return@withContext false
            }

            // Check if the user credentials are valid
            val userExists = userRepository.signIn(intent.email, intent.password)
            sharedPreference.addBoolean(Constans.SAVED_SIGNIN , true)
            Log.d("StockiSIU", "processSignin: ${  intent.email+ intent.password } " )
            return@withContext userExists
        }
    }

   /* suspend fun signIn(intent: SigninIntent): Boolean {
        return withContext(Dispatchers.IO) {
            // Validate user input
            if (!isValidInput(intent)) {
                return@withContext false
            }

            // Check if the user credentials are valid
            val userExists = userRepository.signIn(intent.email, intent.password)
            if (!userExists) {
                return@withContext false
            }

            return@withContext true
        }
    }*/

    private fun isValidInput(intent: SigninIntent): Boolean {
        // Perform validation logic here
        return intent.email.isNotBlank() && intent.password.isNotBlank()
    }
}
