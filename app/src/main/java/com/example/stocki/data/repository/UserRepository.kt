package com.example.stocki.data.repository

import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.stocki.account.signin.SigninState
import com.example.stocki.data.firebase.FirebaseManager
import com.example.stocki.data.firebase.tradingResult
import com.example.stocki.data.pojos.account.PortfolioItem
import com.example.stocki.data.pojos.account.UserInfo
import com.example.stocki.data.sharedpreferences.SharedPreference
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository  @Inject  constructor(private val firebaseManager: FirebaseManager ) {

    companion object {
        @Volatile
        private var instance: UserRepository? = null

        fun getInstance(context: Context,sharedPreference: SharedPreference): UserRepository {
            return instance ?: synchronized(this) {
                instance ?: UserRepository(FirebaseManager.getInstance(context,sharedPreference)).also { instance = it }
            }
        }
    }


    suspend fun sellStock(userId: String , ticker: String  , quantity: Int  , sellingPrice :Double):tradingResult{
        return withContext(Dispatchers.IO){
            firebaseManager.sellStock(userId,ticker,quantity,sellingPrice)
        }

    }
    suspend fun buyStock(userId:String, stock : PortfolioItem): tradingResult {
       return withContext(Dispatchers.IO) {
            firebaseManager.buyStock(userId, stock)
        }
    }
    suspend fun checkUserExists(email: String): Boolean {
       return firebaseManager.checkUserExists(email)
    }
    suspend fun signIn(email: String, password: String): SigninState {
        Log.d("StockiURepo", "signIn: ${  email  + password } ${firebaseManager.signIn(email, password)} " )
        return withContext(Dispatchers.IO){
            firebaseManager.signIn(email, password)
        }
    }

    suspend fun insertUser(name:String , email: String, password: String): Boolean {
        Log.d("StockiURepo", "insertUser: ${email } " )
        return firebaseManager.insertUser(name,email,password)
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseManager.getCurrentUser()
    }

    fun signOut() {
        firebaseManager.signOut()
    }

    suspend fun getUser(uid: String): UserInfo? {
        return firebaseManager.getUser(uid)
    }

    suspend fun  updateUserBalance(userId: String , newBalance: Double){
        Log.d("StockiURepo", "updateUserBalance" )
        firebaseManager.updateUserBalance(userId,newBalance)
    }
    suspend fun getUserBalance(userId: String): Double {
        Log.d("StockiURepo", "getUserBalance")
       return withContext(Dispatchers.IO) {
            firebaseManager.getUserBalance(userId)
        }
    }

    suspend fun getUserPortfolio(userId: String): List<PortfolioItem> {
        Log.d("StockiURepo", "getUserPortfolio")
      return  withContext(Dispatchers.IO) {
            firebaseManager.getUserPortfolio(userId)
        }
    }
    suspend fun updatePortfolio(userId: String , item: PortfolioItem):Boolean {
        return firebaseManager.insertPortfolio(userId,item)
    }
    suspend fun updateUser(user : UserInfo):Boolean {
        return firebaseManager.updateUser(user)
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
