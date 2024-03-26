package com.example.stocki.account.signup

import com.example.stocki.data.pojos.account.UserInfo
import com.example.stocki.data.repository.UserRepository
import com.example.stocki.data.sharedpreferences.SharedPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

class SignupUseCase @Inject constructor(private val userRepository: UserRepository ) {

    suspend fun signupWithEmail(intent: SignupIntent): Boolean {
        return withContext(Dispatchers.IO) {
            // Validate user input
            if (!isValidInput(intent)) {
                return@withContext false
            }

            // Check if the email is already registered
            val userExists = userRepository.checkUserExists(intent.email)
            if (userExists) {
                return@withContext false
            }

            // Create a new user entity
            val user = createUserEntity(intent)

            // Save the user data
            userRepository.insertUser(user)

            return@withContext true
        }
    }
    /*suspend fun signupWithGoogle(intent: SignupIntent): Boolean{
        return withContext(Dispatchers.IO){
            userRepository.
        }

    }*/
    private fun isValidInput(intent: SignupIntent): Boolean {
        // Perform validation logic here
        return intent.email.isNotBlank() &&
                intent.password.isNotBlank() &&
                intent.password == intent.confirmPassword
    }

    private fun createUserEntity(intent: SignupIntent): UserInfo {
        return UserInfo(
            uid = -1,
            name = intent.name,
            email = intent.email,
            password = intent.password
        )
    }
}

