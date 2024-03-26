package com.example.stocki.entrance
import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.stocki.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stocki.account.signup.SignupViewModel

@Composable
    fun EntranceScreen(
        onSignInClick: () -> Unit,
        onSignUpClick: () -> Unit,
       // signUpViewModel: SignupViewModel
) {
   val signUpViewModel: SignupViewModel = hiltViewModel()
    val scope = rememberCoroutineScope()

    val signInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val data = result.data
        //val resultCode = result.resultCode
       signUpViewModel.handleSignInResult(data)
    }
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background photo
            /*Image(
                painter = painterResource(id = androidx.appcompat.R.drawable.),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )*/

            // Content overlay
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Text "Already have an account?"
                Text(
                    text = "Already have an account?",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Sign In button
                Button(
                    onClick = { onSignInClick() },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()
                ) {
                    Text(text = "Sign In")
                }

                // Sign Up button
                Button(
                    onClick = { onSignUpClick() },
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .fillMaxWidth()

                ) {

                    Text(text = "Sign Up")
                }

                // Sign Up with Google button
                Button(
                    onClick = {
                       signUpViewModel.signInWithGoogle(signInLauncher)
                        Log.d("StockiEV", "signInWithGoogleBTN: ${  signInLauncher } " )


                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.googlesignn),
                            contentDescription = "",
                            tint = Color.Transparent,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }
        }
    }

