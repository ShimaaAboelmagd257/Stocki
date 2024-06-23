package com.example.stocki.data.firebase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.example.stocki.account.signin.SigninState
import com.example.stocki.data.pojos.account.PortfolioItem
import com.example.stocki.data.pojos.account.UserInfo
import com.example.stocki.data.pojos.account.userTransaction
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Provides
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
@Singleton
class FirebaseManager @Inject constructor( context: Context) {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val usersReference: DatabaseReference = database.reference.child("User")
    private val fireStore : FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userCollection : CollectionReference = fireStore.collection("User")
    private val context: Context = context.applicationContext
   // private val intialBalance :Double = 100.00
    companion object {
        @Volatile
        private var instance: FirebaseManager? = null

        fun getInstance(context: Context): FirebaseManager {
            return instance ?: synchronized(this) {
                instance ?: FirebaseManager(context).also { instance = it }
            }
        }
    }
   /* suspend fun createUser(email: String, password: String ,intialBalance :Double = 100.00): Boolean {
        return try {
           val result = mAuth.createUserWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid?: throw IllegalStateException("User Id shouldn't be NULL")
            true
        } catch (e: Exception) {
            false
        }
    }*/

    suspend fun signIn(email: String,password: String):SigninState{
        return try {
            val result = mAuth.signInWithEmailAndPassword(email,password).await()
            val userId = result.user?.uid?: throw IllegalStateException("User Id shouldn't be null")
            Log.d("StockiFirebaseDataHandle", "Login success for userId ${userId}")

            SigninState.Success
        }catch (e:Exception){
            SigninState.Error(e.message?:"Error loging the User because ${e.message}")

        }




    }

   /* suspend fun signIn(email: String, pass: String): SigninState {
        val splitEmail = email.split("\\.".toRegex()).toTypedArray()
        val root = splitEmail[0]

        return try {
            val snapshot = usersReference.get().await()
            if (snapshot.exists()) {
                val userSnapshot = snapshot.child(root)
                if (userSnapshot.exists()) {
                    val userModel1 = userSnapshot.getValue(UserInfo::class.java)

                    if (userModel1 != null &&  userModel1.email == email) {
                        Log.d("StockiFirebaseDataHandle", "Login success")
                        SigninState.Success
                    } else {
                        Log.d("StockiFirebaseDataHandle", "Invalid credentials")
                        SigninState.WrongPassword
                    }
                } else {
                    Log.d("StockiFirebaseDataHandle", "User data not found")
                    SigninState.UserNotFound
                }
            } else {
                SigninState.Error("Error")
            }
        } catch (e: Exception) {
            Log.e("StockiFirebaseDataHandle", "Error: ${e.message}")
            SigninState.Error(e.message!!)
        }
    }
*/


    suspend fun signUpWithGoogle( account: GoogleSignInAccount):Boolean {
        return try {
            val firebaseCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null)
            mAuth.signInWithCredential(firebaseCredential).await()
            Log.d("StockiFirebaseDataHandle", "signUpWithGoogle: " + account)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("StockiFirebaseDataHandle", "Error signUpWithGoogle: " + e.message)

            false
        }
    }
    suspend fun handleGoogleSignInResult( data: Intent?): Boolean {
       /* if (requestCode == 1) {*/
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.await()
                Log.d("StockiFirebaseDataHandle", " handleGoogleSignInResult: " + account)

                return signUpWithGoogle(account)
            } catch (e: ApiException) {
                Log.e("StockiFirebaseDataHandle", "Error handleGoogleSignInResult: " + e.message)

                e.printStackTrace()
            }

        return false
    }

     fun getGoogleSignInIntent(): Intent {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("124054707468-7jeb0b7nk3s385ldun1vfbrbqg9ocmjk.apps.googleusercontent.com")
            .requestEmail()
            .build()
        val googleSignInClient = GoogleSignIn.getClient(context, gso)
         Log.d("StockiFirebaseDataHandle", "getGoogleSignInIntent: ${googleSignInClient.signInIntent} " )

         return googleSignInClient.signInIntent
    }

    suspend fun insertUser(name:String , email: String, password: String): Boolean{
      return try {
          val result = mAuth.createUserWithEmailAndPassword(email, password).await()
          val userId = result.user?.uid?: throw IllegalStateException("User Id shouldn't be NULL")
           val user = UserInfo(uid = userId , email = email,name = name , balance = 100.0)
            userCollection.document(userId).set(user).await()
           Log.d("FirebaseDataHandle", "Value inserted for UserId is: ${userId}")
           true
       } catch (e: Exception) {
           Log.e("FirebaseDataHandle", "Error inserting user: ${e.message}")
           false
       }
   }
    @SuppressLint("SuspiciousIndentation")
    suspend fun recordTransaction(userId: String, transaction: userTransaction){
     val transactionRef = userCollection.document(userId).collection("transaction").document()
        transactionRef.set(transaction).await()

    }
    //trading virtually ;)
    suspend fun sellStock(userId: String , ticker: String  , quantity: Int  , sellingPrice :Double){
        val portfolioRef = userCollection.document(userId).collection("portfolio").document(ticker)
        val snapshot = portfolioRef.get().await()

        if (snapshot.exists()){
            val existStock = snapshot.toObject(PortfolioItem::class.java)!!
            if(existStock.quantity >= quantity){
                val newQuntity = existStock.quantity - quantity
                val totalRevnue = sellingPrice * quantity
                val userRef = userCollection.document(userId)
                val snapshot = userRef.get().await()
                val user = snapshot.toObject(UserInfo::class.java)!!
                val newBalance = user.balance +totalRevnue
                userRef.update("balance" , newBalance).await()

                if (newQuntity > 0){
                    portfolioRef.update("quantity" , newQuntity).await()
                } else {
                    portfolioRef.delete().await()
                }
                    recordTransaction(userId,userTransaction("sell",ticker,quantity,sellingPrice,System.currentTimeMillis()))
            }else{
                throw IllegalStateException("Not enough stock quantity to sell")
            }
        }else{
            throw IllegalStateException("Stock Not Found in user PORTFOLIO")
        }


    }
suspend fun buyStock(userId:String, stock : PortfolioItem){
   val userRef=  userCollection.document(userId)
    val userSnapshot = userRef.get().await()
    val user = userSnapshot.toObject(UserInfo::class.java)!!

    val totalCost = stock.averagePrice * stock.quantity
    if(user.balance >=  totalCost){
        val newBalance = user.balance - totalCost
        userRef.update("balance",newBalance).await()

        val userPrortfolioRef = userCollection.document(userId).collection("portfolio")
        val stockRef = userPrortfolioRef.document(stock.ticker)
        val snapshot = stockRef.get().await()

        if (snapshot.exists()){
            val existStock = snapshot.toObject(PortfolioItem::class.java)!!
            val newQuantity = existStock.quantity + stock.quantity
            val newPrice = (existStock.averagePrice * existStock.quantity + stock.averagePrice * stock.quantity ) / newQuantity
            stockRef.update("quantity" , newQuantity , "averagePrice" , newPrice).await()

        }else{
            stockRef.set(stock).await()
        }
        recordTransaction(userId,userTransaction("buy",stock.ticker,stock.quantity,stock.averagePrice,System.currentTimeMillis()))
    }else{
        throw IllegalStateException("Error in balance calculation ")

    }

}


    fun getCurrentUser(): FirebaseUser? {
        Log.d("StockiFirebaseDataHandle", "Value inserted is: " + mAuth.currentUser)
        return mAuth.currentUser
    }


    fun signOut() {
        mAuth.signOut()
    }


   suspend fun getUser(uid: String): UserInfo? {
       return try {
           val snapshot = userCollection.document(uid).get().await()
           snapshot.toObject(UserInfo::class.java)?: throw IllegalStateException("User Not Found")
       } catch (e: Exception) {
           null
       }
   }

    suspend fun  updateUserBalance(userId: String , newBalance: Double){
        userCollection.document(userId).update("balance" , newBalance).await()
    }
    suspend fun getUserBalance(userId: String): Double{
        val snapshot = userCollection.document(userId).get().await()
        return snapshot.toObject(UserInfo::class.java)?.balance?: 0.0
    }

    suspend fun getUserPortfolio(userId: String): List<PortfolioItem>{
        val snapshot = userCollection.document(userId).collection("portfolio").get().await()
        return snapshot.documents.map { doc ->
            doc.toObject(PortfolioItem::class.java)!!

        }
    }
   /* suspend fun checkUserExists(email: String): Boolean {
        return try {
            val dataSnapshot = usersReference.orderByChild("email").equalTo(email).get().await()
            Log.d("StockiFirebaseDataHandle", "checkUserExists: " + dataSnapshot.exists())

            dataSnapshot.exists()

        } catch (e: Exception) {
            false
        }
    }*/
     fun getGoogleSignInAccount(data: Intent?): GoogleSignInAccount? {
        // Extract GoogleSignInAccount from the intent data
        return data?.let { GoogleSignIn.getSignedInAccountFromIntent(it).getResult(ApiException::class.java) }
    }

    private suspend fun <T> Task<T>.await(): T {
        return suspendCancellableCoroutine { continuation ->
            addOnSuccessListener { result ->
                continuation.resume(result)
            }
            addOnFailureListener { e ->
                continuation.resumeWithException(e)
            }
        }
    }
}
