package com.example.stocki.account.signin

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.stocki.NavigationRoute
import com.example.stocki.account.signup.SignupState
import com.example.stocki.data.sharedpreferences.SharedPreference
import com.example.stocki.data.sharedpreferences.SharedPreferences
import com.example.stocki.utility.Constans

@Composable
    fun SignInScreen(
        signinViewModel: SigninViewModel,
        navController: NavController,
       // onSignInSuccess: ()-> Unit
    ) {
        // Local state to hold email and password
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        val viewState by signinViewModel.viewState.collectAsState()
        var showProgress by remember { mutableStateOf(false) }


    LaunchedEffect(viewState) {
        if (viewState is SigninState.Success) {
            navController.navigate(NavigationRoute.Main.route)
        }
    }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") }
            )
            Button(
                onClick = { signinViewModel.processSignin(SigninIntent(email, password))
                   // showProgress = true
                    Log.d("StockiSIV", "processSignin: ${  email+ password } " )

                }
            ) {

                Text("Sign In")
            }
            if (showProgress) {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }
            when (val state = viewState) {
                is SigninState.Loading -> {
                    CircularProgressIndicator()
                }
                is SigninState.Success -> {
                   // onSignInSuccess
                     ///sharedPreference.addBoolean(Constans.SAVED_SIGNIN,true)
                    Log.d("StockiSignINV"," , ${email} , ${password} ")
                  //  Log.d("StockiSignINV"," , ${sharedPreference.getBoolean(Constans.SAVED_SIGNIN,false)} ")


                }
                is SigninState.Error -> {
                    // Show error message
                    Log.d("StockiSUV", "SignupState: error " + state.errorMessage )

                }
                else -> {}
            }
        }
        }


// Render UI based on ViewState
/* when (viewState) {
     is SigninState.Loading -> ProgressBar()
     is SigninState.Success -> {
         Toast.makeText(LocalContext.current, "Sign in successfully", Toast.LENGTH_SHORT).show()
         // Navigate to next screen
     }
     is SigninState.Error -> {
       //  Toast.makeText(LocalContext.current, "Sign in failed: ${viewState.errorMessage}", Toast.LENGTH_SHORT).show()
     }
     else -> {}
 }*/
/* LaunchedEffect(viewState) {
     if (viewState is SigninState.Success) {
        // navController.navigate(NavigationRoute.Main.route)
         onSignInSuccess
     }
 }*/