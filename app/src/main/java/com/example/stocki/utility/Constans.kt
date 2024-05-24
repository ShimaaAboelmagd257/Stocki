package com.example.stocki.utility

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import androidx.compose.runtime.*
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation

object Constans {
    val Api_Key = "mAaVmZBxHnrQrIqmCBEuRgklMFIC4_yH"
    //8ahp5hAO71Ra1I0L0ePYgWxaW2mJCCO3
    val Base_Url = "https://api.polygon.io/"
    val SAVED_SIGNIN = "SAVED_SIGNIN"
    val STOCK_MARKET = "STOCK_MARKET"
    val DIVIDENDS = "DIVIDENDS"
    val SPLITS = "SPLITS"
    val TYPES ="TYPES"
    val STATUS = "STATUS"
    val BUY_SELL = "BUY_SELL"
    val STOCK_EXCHANGE ="STOCK_EXCHANGE"
    val stockMarketUrl = "p7HKvqRI_Bo"
    val dividensUrl = "zd0n2rpt_qM"
    val splitsUrl = "vZhtCBJTwk4"
    val typesUrl = "Ecsk4kO0flg"
    val statusUrl = ""
    val buy_sellUrl = "lAcwiWEiGQg"
     val stockExchangeUrl = "F3QpgXBtDeo"

    const val DEFAULT_IMAGE_URL = "R.drawable.ic_launcher_background"
    const val MARKETS_SCREEN = "markets_screen"
    const val TICKER_INFO_SCREEN = "ticker_info_screen"
    val WINDOW = 10
    val SERIES_TYPE = "close"
    val ORDER = "desc"
    val ADUJTED = true
    val SHORT_WINDOW = 12
    val LONG_WINDOW = 26
    val SIGNAL_WINDOW = 9
    val TIMESPAN = "day"
    /*fun timestampToDate(timestamps: List<Long>): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return timestamps.map {
            val instant = Instant.ofEpochMilli(it)
            val dateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime()
            formatter.format(dateTime)
        }.toString()
    }*/
    fun timestampsToDate(timestamps: List<Long>): List<String> {
        val formatter = DateTimeFormatter.ofPattern("MM-dd")
        return timestamps.map {
            val instant = Instant.ofEpochMilli(it)
            val dateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime()
            formatter.format(dateTime)
        }
    }
    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}\$")
        return emailRegex.matches(email)
    }

    fun isValidPassword(password: String): Boolean {
        return password.length >= 6
    }
    @Composable
    fun LoadNetworkImage(url: String, modifier: Modifier = Modifier) {
        val painter = rememberImagePainter(
            data = url,
            builder = {
                transformations(CircleCropTransformation()) // Apply transformations if needed
            }
        )

        Image(
            painter = painter,
            contentDescription = null, // Content description is optional
            modifier = modifier
        )
    }

    @Composable
    fun ReadMoreText(
        text: String,
        maxLines: Int = 4,
        onReadMoreClicked: () -> Unit
    ) {
        var expanded by remember { mutableStateOf(false) }

        Text(
            text = text,
            maxLines = if (expanded) Int.MAX_VALUE else maxLines,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.clickable {
                expanded = !expanded
            }
        )

        if (!expanded && text.length > maxLines * 20) { // Assuming each line has 20 characters
            Text(
                text = "Read More",
                color = MaterialTheme.colors.primary,
                modifier = Modifier.padding(top = 4.dp).clickable {
                    onReadMoreClicked()
                    expanded = true
                }
            )
        }
    }


}