package com.example.stocki.account.profile

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel



@Composable
fun ProfileView(viewModel: ProfileViewmodel = hiltViewModel() , userId:String) {
    val state by viewModel.state.collectAsState()
    val portfolioState by viewModel.portfolioState.collectAsState()

   LaunchedEffect(userId ){
       viewModel.fetchUserData(userId)
       viewModel.fetchUserPortfolio(userId)
   }

    when(val profileState = state){
        is ProfileState.Data -> {
            Column(modifier = Modifier
                .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                val profileInfo = profileState.user
                   Text(text = profileInfo.name)
                   Text(text = profileInfo.email)
                   Text(text = profileInfo.balance.toString())
            }
            }
        is ProfileState.Error -> {
            Log.d("StockiProfileView", "Error in ProfileView  ${profileState.error}")
        }
        ProfileState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(150.dp,300.dp))
        }
    }

}