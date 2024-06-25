package com.example.stocki.account.signup
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.setValue
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.stocki.NavigationRoute
import com.example.stocki.R
import com.example.stocki.account.signin.SigninState


@Composable
    fun SignUpScreen(signUpViewModel: SignupViewModel = hiltViewModel() , navController: NavController) {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        val viewState by signUpViewModel.viewState.observeAsState()

    if (viewState is SignupState.Success) {
        navController.navigate(NavigationRoute.Main.route)
    }
        Image(
            painter = painterResource(id = R.drawable.greensecondrayback),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name ") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(
                    "Confirm Password" ,
                color = Color.White) },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.Black),
                textStyle = TextStyle(color = Color.White)

            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                val intent = SignupIntent(name,email, password, confirmPassword)
                signUpViewModel.processSignUp(intent)
            }) {
                Text("Sign Up")
            }
            // Observe signupViewState and react accordingly
            when (val state = viewState) {
                is SignupState.Loading -> {
                    CircularProgressIndicator()
                }
                is SignupState.Success -> {
                    Toast.makeText(LocalContext.current, "Signup successfully ", Toast.LENGTH_SHORT).show()
                    Log.d("StockiSignUp","${name} , ${email} , ${confirmPassword}")

                }
                is SignupState.Error -> {
                    // Show error message
                    Toast.makeText(LocalContext.current, state.error, Toast.LENGTH_SHORT).show()
                    Log.d("StockiSUV", "SignupState: ${  state.error } " )

                }
                else -> {}
            }
        }
    }

