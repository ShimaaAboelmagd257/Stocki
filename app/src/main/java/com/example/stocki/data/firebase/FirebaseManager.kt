package com.example.stocki.data.firebase

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import com.example.stocki.account.signin.SigninState
import com.example.stocki.data.pojos.account.UserInfo
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
    private val context: Context = context.applicationContext

    companion object {
        @Volatile
        private var instance: FirebaseManager? = null

        fun getInstance(context: Context): FirebaseManager {
            return instance ?: synchronized(this) {
                instance ?: FirebaseManager(context).also { instance = it }
            }
        }
    }
    suspend fun createUser(email: String, password: String): Boolean {
        return try {
            mAuth.createUserWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun signIn(email: String, pass: String): SigninState {
        val splitEmail = email.split("\\.".toRegex()).toTypedArray()
        val root = splitEmail[0]

        return try {
            val snapshot = usersReference.get().await()
            if (snapshot.exists()) {
                val userSnapshot = snapshot.child(root)
                if (userSnapshot.exists()) {
                    val userModel1 = userSnapshot.getValue(UserInfo::class.java)

                    if (userModel1 != null && userModel1.password == pass && userModel1.email == email) {
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

    suspend fun insertUser(user: UserInfo): Boolean{
      return try {
           val route = user.email.split(".")
           val key = route[0]
           val databaseReference = database.reference.child("User").child(key)
           // Set the user data
          databaseReference.setValue(user)

           Log.d("FirebaseDataHandle", "Value inserted is: ${user.email}")
           true
       } catch (e: Exception) {
           Log.e("FirebaseDataHandle", "Error inserting user: ${e.message}")
           false
       }
   }



    fun getCurrentUser(): FirebaseUser? {
        Log.d("StockiFirebaseDataHandle", "Value inserted is: " + mAuth.currentUser)

        return mAuth.currentUser
    }

    fun signOut() {
        mAuth.signOut()
    }


   suspend fun getUser(uid: Int): UserInfo? {
       return try {
           val dataSnapshot = usersReference.child(uid.toString()).get().await()
           dataSnapshot.getValue(UserInfo::class.java)
       } catch (e: Exception) {
           null
       }
   }
    suspend fun checkUserExists(email: String): Boolean {
        return try {
            val dataSnapshot = usersReference.orderByChild("email").equalTo(email).get().await()
            Log.d("StockiFirebaseDataHandle", "checkUserExists: " + dataSnapshot.exists())

            dataSnapshot.exists()

        } catch (e: Exception) {
            false
        }
    }
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
