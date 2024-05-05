package com.example.stocki.account.signin

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.setValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.stocki.LocalSharedPreferences
import com.example.stocki.NavigationRoute
import com.example.stocki.R
import com.example.stocki.account.signup.SignupState
import com.example.stocki.data.sharedpreferences.SharedPreference
import com.example.stocki.data.sharedpreferences.SharedPreferences
import com.example.stocki.utility.Constans
import kotlinx.coroutines.launch

@Composable
    fun SignInScreen(
        signinViewModel: SigninViewModel,
        navController: NavController,
    ) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val viewState by signinViewModel.viewState.collectAsState()
    var showProgress by remember { mutableStateOf(false) }
    val sharedPreferences = LocalSharedPreferences.current
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    LaunchedEffect(viewState) {
        if (viewState is SigninState.Success) {
            navController.navigate(NavigationRoute.Main.route)
        }
    }
    Image(
        painter = painterResource(id = R.drawable.greensecondrayback),
        contentDescription = null,
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )

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
                    onClick = {
                        if (email.isNotBlank() && password.isNotBlank()) {
                            if (Constans.isValidEmail(email)){
                            signinViewModel.processSignin(SigninIntent(email, password))
                            Log.d("StockiSIV", "processSignin: ${email + password} ")

                        } }else {
                            Toast.makeText(context, "Please fill all the data", Toast.LENGTH_SHORT).show()

                        }
                    }
                )
                        {

                    Text("Sign In")
                }


            when (val state = viewState) {
                is SigninState.Loading -> {
                    CircularProgressIndicator()
                }
                is SigninState.Success -> {
                    sharedPreferences.addBoolean(Constans.SAVED_SIGNIN, true)
                    Toast.makeText(context, "Login succcessfully", Toast.LENGTH_SHORT).show()
                    Log.d("StockiSignINV", " , ${email} , ${password} ")
                }
                is SigninState.WrongPassword -> {
                    Toast.makeText(context, "Wrong Password", Toast.LENGTH_SHORT).show()
                }
                is SigninState.UserNotFound ->{
                    Toast.makeText(context, "User not Found", Toast.LENGTH_SHORT).show()

                }
                is SigninState.Error -> {
                    sharedPreferences.addBoolean(Constans.SAVED_SIGNIN, false)
                    Toast.makeText(context, "Error Loging in", Toast.LENGTH_SHORT).show()

                    Log.d("StockiSUV", "SignupState: error " + state.errorMessage)
                }
                else -> {}
            }
        }
    }



