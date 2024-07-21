package com.example.stocki.account.profile

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import androidx.lifecycle.MutableLiveData
import com.example.stocki.data.pojos.account.PortfolioItem
import com.example.stocki.data.pojos.account.userTransaction
import com.example.stocki.ticker.tickerinfo.TickerInfoViewModel

@Composable
fun TransactionView(viewmodel: TickerInfoViewModel = hiltViewModel(), ticker:String,userId:String ,price:Double) {
    val toastMessage = remember { MutableLiveData<String>() }
    var quantity by remember { mutableStateOf(TextFieldValue("")) }
    var showDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(toastMessage) {
        toastMessage.observeForever { message ->
            message?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
    Column(modifier = Modifier.padding(16.dp)) {
        Card(
            shape = RoundedCornerShape(15.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
           // elevation = CardDefaults.cardElevation(8.dp) // Use CardDefaults for elevation
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(color = Color.Black)

            ) {
                Text(text = ticker, color = Color.White, style = MaterialTheme.typography.h4)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "$price", color = Color.White, style = MaterialTheme.typography.h5)
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text(text = "Quantity") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = Color.White,
                        backgroundColor = Color.DarkGray,
                        focusedIndicatorColor = Color.White,
                        unfocusedIndicatorColor = Color.Gray,
                        cursorColor = Color.White
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        NumericKeypad(
            onNumberClick = { number ->
                quantity = TextFieldValue(quantity.text + number)
            },
            onDeleteClick = {
                if (quantity.text.isNotEmpty()) {
                    quantity = TextFieldValue(quantity.text.dropLast(1))
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                showDialog = true
            }
        ) {
            Text(text = "Buy")
        }

        if (showDialog) {
            val qt = quantity.text.toIntOrNull()
            if (qt != null) {
                ConfirmationDialog(
                    ticker = ticker,
                    quantity = qt,
                    price = price,
                    onConfirm = {
                        viewmodel.buyStock(
                            userId, PortfolioItem(
                                ticker = ticker,
                                quantity = qt,
                                averagePrice = price
                            )
                        )
                        toastMessage.value = "Transaction Successful!"
                        showDialog = false
                    },
                    onDismiss = {
                        toastMessage.value = "Transaction dismiss!"
                        showDialog = false
                    }
                )
            }
        }
    }
}
@Composable
fun NumericKeypad(onNumberClick: (String) -> Unit, onDeleteClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val numbers = listOf(
            listOf("1", "2", "3"),
            listOf("4", "5", "6"),
            listOf("7", "8", "9"),
            listOf("00", "0", "X")
        )
        numbers.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { number ->
                    Button(
                        onClick = {
                            when (number) {
                                "X" -> onDeleteClick()
                                else -> onNumberClick(number)
                            }
                        },
                        modifier = Modifier
                            .size(64.dp)
                            .background(Color.Black)
                    ) {
                        Text(text = number, fontSize = 18.sp)
                    }
                }
            }
        }
    }
}
@Composable
fun ConfirmationDialog(
    ticker: String,
    quantity: Int,
    price: Double,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Confirm Transaction") },
        text = {
            Column {
                Text(text = "Ticker: $ticker")
                Text(text = "Quantity: $quantity")
                Text(text = "Price: $price")
                Text(text = "Total: ${quantity * price}")
            }
        },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text(text = "Confirm")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}
@Composable
fun TransactionItem(transaction: userTransaction) {
    
}