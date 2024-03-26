package com.example.stocki.account.signup

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.example.stocki.data.repository.UserRepository
import com.example.stocki.data.sharedpreferences.SharedPreference
import com.example.stocki.utility.Constans
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignupWithGoogleUseCase @Inject constructor(private val userRepository: UserRepository, private val sharedPreference: SharedPreference) {
    suspend fun signInWithGoogle(data: Intent?): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val success = userRepository.handleGoogleSignInResult(data)
                if (success) {
                    sharedPreference.addBoolean(Constans.SAVED_SIGNIN, true)
                }
                success
            } catch (e: Exception) {
                // Handle error appropriately, maybe log it
                false
            }
        }
    }
    suspend fun signInWithGoogle(activityResultLauncher: ActivityResultLauncher<Intent>): Boolean {
        return try {
            val signInIntent = userRepository.getGoogleSignInIntent()
            activityResultLauncher.launch(signInIntent)
            true // Assuming launching the intent is successful
        } catch (e: Exception) {
            // Handle error appropriately, maybe log it
            false
        }
    }

    fun getGoogleAccount(data: Intent?): GoogleSignInAccount? {
        return try {
            userRepository.getGoogleAcount(data)
        } catch (e: Exception) {
            // Handle error appropriately, maybe log it
            null
        }
    }
}
