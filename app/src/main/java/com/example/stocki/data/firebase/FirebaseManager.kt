package com.example.stocki.data.firebase

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
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

    suspend fun signIn(email: String, pass: String): Boolean {
        val splitEmail = email.split("\\.".toRegex()).toTypedArray()
        val root = splitEmail[0]

      //  val databaseReference = FirebaseDatabase.getInstance().getReference("User")
        return try {
            val snapshot = usersReference.get().await()
            if (snapshot.exists()) {
                val userSnapshot = snapshot.child(root)
                if (userSnapshot.exists()) {
                    val userModel1 = userSnapshot.getValue(UserInfo::class.java)

                    if (userModel1 != null && userModel1.password == pass && userModel1.email == email) {
                        Log.d("StockiFirebaseDataHandle", "Login success")
                        true
                    } else {
                        Log.d("StockiFirebaseDataHandle", "Invalid credentials")
                        false
                    }
                } else {
                    Log.d("StockiFirebaseDataHandle", "User data not found")
                    false
                }
            } else {
                false
            }
        } catch (e: Exception) {
            Log.e("StockiFirebaseDataHandle", "Error: ${e.message}")
            false
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
/*fun insertUser(user: UserInfo, callback: FirebaseCallback) {
       usersReference.child(user.uid.toString()).setValue(user)
           .addOnCompleteListener { task ->
               if (task.isSuccessful) {
                   callback.onSuccess()
               } else {
                   callback.onFailure(task.exception?.message ?: "Unknown error")
               }
           }
   }*/

/* fun getUser(uid: Int, callback: FirebaseUserCallback) {
     usersReference.child(uid.toString()).addListenerForSingleValueEvent(object : ValueEventListener {
         override fun onDataChange(dataSnapshot: DataSnapshot) {
             val user = dataSnapshot.getValue(UserInfo::class.java)
             if (user != null) {
                 callback.onSuccess(user)
             } else {
                 callback.onNotFound()
             }
         }

         override fun onCancelled(databaseError: DatabaseError) {
             callback.onFailure(databaseError.message)
         }
     })
 }*/

/* suspend fun getUser(uid: Int): UserInfo? {
     return try {
        // val user = dataSnapshot.getValue<UserInfo>()
         val dataSnapshot = usersReference.child(uid.toString())//.getValue(UserInfo::class.java).await()
         dataSnapshot.getValue(UserInfo::class.java)
     } catch (e: Exception) {
         null
     }
 }
*/
/*fun checkUserExists(email: String, callback: UserExistCallback) {
    usersReference.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            callback.onResult(dataSnapshot.exists())
        }

        override fun onCancelled(databaseError: DatabaseError) {
            callback.onFailure(databaseError.message)
        }
    })
}*/

/*fun createUser(email: String, password: String, callback: AuthCallback) {
       mAuth.createUserWithEmailAndPassword(email, password)
           .addOnCompleteListener { task ->
               if (task.isSuccessful) {
                   callback.onAuthSuccess()
               } else {
                   task.exception?.let { callback.onAuthFailure(it.message ?: "Unknown error") }
               }
           }
   }

   fun signIn(email: String, password: String, callback: AuthCallback) {
       mAuth.signInWithEmailAndPassword(email, password)
           .addOnCompleteListener { task ->
               if (task.isSuccessful) {
                   callback.onAuthSuccess()
               } else {
                   task.exception?.let { callback.onAuthFailure(it.message ?: "Unknown error") }
               }
           }
   }
*/

/*suspend fun signIn(email: String, password: String): Boolean {
        return try {
            mAuth.signInWithEmailAndPassword(email, password).await()
            Log.d("StockiFirebaseDataHandle", "signIn: " + email + password)

            true
        } catch (e: Exception) {
            Log.d("StockiFirebaseDataHandle", "erroe signIn: " + e.message)

            false
        }
    }*/
/*private fun setUserValues(databaseReference: DatabaseReference, userPojo: UserInfo) {
    // Set user data to the DatabaseReference
    databaseReference.setValue(userPojo)
}*/

/*
suspend fun insertUser(user: UserInfo): Boolean {
   return try {
       // Get the user's UID from FirebaseAuth
       val uid = mAuth.currentUser?.uid ?: throw IllegalStateException("User not authenticated")

       // Set the user data under a child node with the user's UID
       usersReference.child(uid).setValue(user).await()
       Log.d("StockiFirebaseDataHandle", "User inserted: $user")

       true
   } catch (e: Exception) {
       Log.e("StockiFirebaseDataHandle", "Error inserting user: $e")
       false
   }
}*/
/*suspend fun insertUser(user: UserInfo): Boolean {
    return try {
        usersReference.child(user.uid.toString()).setValue(user).await()
        Log.d("StockiFirebaseDataHandle", "Value inserted is: " + user.email)

        true
    } catch (e: Exception) {
        false
    }
}*/

/* suspend fun signInWithGoogle(activityResultLauncher: ActivityResultLauncher<Intent>) {
       try {
           // Configure Google Sign-In options
           val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken("124054707468-7jeb0b7nk3s385ldun1vfbrbqg9ocmjk.apps.googleusercontent.com")
               .requestEmail()
               .build()

           // Initialize GoogleSignInClient with the configured options
           val googleSignInClient = GoogleSignIn.getClient(context, gso)

           // Start Google Sign-In activity
           val signInIntent = googleSignInClient.signInIntent
           activityResultLauncher.launch(signInIntent)
       } catch (e: Exception) {
           e.printStackTrace()
       }
   }*/
/*suspend fun login(context: Context, email: String, pass: String): Boolean {
        val splitEmail = email.split("\\.".toRegex()).toTypedArray()
        val root = splitEmail[0]

        val databaseReference = FirebaseDatabase.getInstance().getReference("User")
        var isSucceeded = false

        try {
            val snapshot = databaseReference.get().await()
            if (snapshot.exists()) {
                val userSnapshot = snapshot.child(root)
                if (userSnapshot.exists()) {
                    try {
                        val userModel1 = userSnapshot.getValue(UserInfo::class.java)

                        if (userModel1 != null && userModel1.password == pass && userModel1.email == email) {
                            isSucceeded = true

                            Log.d("FirebaseData", "Login success")

                        } else {
                            isSucceeded = false
                            Log.d("FirebaseData", "Invalid credentials")
                        }
                    } catch (e: DatabaseException) {
                        Log.e("FirebaseData", "Error converting to UserPojo: " + e.message)
                        isSucceeded = false
                    }
                } else {
                    Log.d("FirebaseData", "User data not found")
                    isSucceeded = false
                }
            }
        } catch (e: Exception) {
            Log.e("FirebaseData", "Database error: " + e.message)
            isSucceeded = false
        }

        return isSucceeded
    }*/