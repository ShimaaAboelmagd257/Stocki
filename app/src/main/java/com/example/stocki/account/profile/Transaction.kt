package com.example.stocki.account.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.stocki.data.pojos.account.PortfolioItem
import com.example.stocki.data.pojos.account.userTransaction
import com.example.stocki.ticker.tickerinfo.TickerInfoViewModel

@Composable
fun TransactionView(viewmodel: TickerInfoViewModel = hiltViewModel(), ticker:String,userId:String ,price:Double) {
    var quantity by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "ticker : " + ticker)
        Spacer(modifier = Modifier.height(8.dp))
        TextField(value = quantity, onValueChange = {quantity = it}, label = { Text(text = "Quantity")})
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { val qt = quantity.toIntOrNull()
        if(qt !=null){
            viewmodel.buyStock(userId, PortfolioItem(
                ticker = ticker,
                quantity = qt,
                averagePrice = price
            ))
        }}) {

        }
    }
}

@Composable
fun TransactionItem(transaction: userTransaction) {
    
}