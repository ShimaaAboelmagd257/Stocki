package com.example.stocki.data.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.stocki.account.signin.SigninState
import com.example.stocki.data.firebase.FirebaseManager
import com.example.stocki.data.pojos.account.UserInfo
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository  @Inject  constructor(private val firebaseManager: FirebaseManager) {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(context: Context): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository(FirebaseManager.getInstance(context)).also { instance = it }
            }
        }
    }

    suspend fun createUser(email: String, password: String): Boolean {
        return firebaseManager.createUser(email, password)
    }

    suspend fun signIn(email: String, password: String): SigninState {
        Log.d("StockiURepo", "signIn: ${  email  + password } ${firebaseManager.signIn(email, password)} " )
        return firebaseManager.signIn(email, password)
    }

    suspend fun insertUser(user: UserInfo): Boolean {
        Log.d("StockiURepo", "insertUser: ${  user.email } " )
        return firebaseManager.insertUser(user)
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseManager.getCurrentUser()
    }

    fun signOut() {
        firebaseManager.signOut()
    }

    suspend fun getUser(uid: Int): UserInfo? {
        return firebaseManager.getUser(uid)
    }

    suspend fun checkUserExists(email: String): Boolean {
        Log.d("StockiURepo", "checkUserExists: ${  email } ${firebaseManager.checkUserExists(email)} " )

        return firebaseManager.checkUserExists(email)
    }
      fun getGoogleSignInIntent(): Intent {
         Log.d("StockiURepo", "getGoogleSignInIntent " )
         return firebaseManager.getGoogleSignInIntent()
    }

     suspend fun handleGoogleSignInResult( data: Intent?): Boolean {
         Log.d("StockiURepo", "handleGoogleSignInResult  ${firebaseManager.handleGoogleSignInResult( data)}" )

         return firebaseManager.handleGoogleSignInResult( data)
    }

    fun getGoogleAcount(data: Intent?): GoogleSignInAccount?{
        Log.d("StockiURepo", "getGoogleAcount  ${firebaseManager.getGoogleSignInAccount(data)}" )
        return firebaseManager.getGoogleSignInAccount(data)
    }
}
