package com.example.stocki.utility

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
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
    const val CACHE_EXPIRY_TIME_HOUR = 60 * 60 * 1000 // 1 hour in milliseconds
    const val CACHE_EXPIRY_TIME_DAY = 60 * 60 * 1000 *24 // 1 hour in milliseconds

    val  currentTime = System.currentTimeMillis()
    val currentAdujestedTime = System.currentTimeMillis() - (CACHE_EXPIRY_TIME_DAY *3)
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

    fun timestampToDate(timestamp: Long): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return timestamp.let {
            val instant = Instant.ofEpochMilli(it)
            val dateTime = instant.atOffset(ZoneOffset.UTC).toLocalDateTime()
            formatter.format(dateTime)
        }.toString()
    }
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
    fun LoadSvgImageWithFallback(
        imageUrl: String?,
        fallbackImage: Painter,
        modifier: Modifier = Modifier
    ) {
        val painter: Painter = rememberAsyncImagePainter(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(imageUrl)
                .decoderFactory(SvgDecoder.Factory())
                .build()
        )
        if (imageUrl.isNullOrEmpty()) {
            Image(
                painter = fallbackImage,
                contentDescription = null,
                modifier = modifier
            )
        } else {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = modifier.fillMaxSize()
            )
        }
    }
    @Composable
    fun LoadpngImageWithFallback(
        imageUrl: String?,
        fallbackImage: Painter,
        modifier: Modifier = Modifier
    ) {
        if (imageUrl.isNullOrEmpty()) {
            Image(
                painter = fallbackImage,
                contentDescription = null,
                modifier = modifier
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = null,
                modifier = modifier
            )
        }
    }
    @Composable
    fun LoadNetworkSvgImage(url: String, modifier: Modifier = Modifier) {
        val painter: Painter = rememberAsyncImagePainter(
            model = ImageRequest
                .Builder(context = LocalContext.current)
                .data(url)
                .decoderFactory(SvgDecoder.Factory())
                .build()
        )
        Box(
            modifier = modifier
        )
        {
            Image(
                painter = painter,
                contentDescription = null, // Content description is optional
                modifier = modifier,
                contentScale = ContentScale.FillHeight
            )
        }
    }

    @Composable
    fun LoadNetworkImage(url: String, modifier: Modifier = Modifier) {
           val painter: Painter = rememberImagePainter(data = url)
           Box(
               modifier = modifier
           )
           {
               Image(
                   painter = painter,
                   contentDescription = null, // Content description is optional
                   modifier = modifier,
                   contentScale = ContentScale.Fit
               )
           }
    }
    @Composable
    fun ClickableUrl(
        url : String?,
        modifier: Modifier =Modifier
    ){
        val context = LocalContext.current

        val annotation = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Blue , fontSize = 16.sp)){
                append(url)
            }
            url?.let {
                addStringAnnotation(
                    tag = "URL" ,
                    annotation = url,
                    start = 0,
                    end = it.length
                )
            }
        }

        ClickableText(text = annotation, onClick = { offset ->
            annotation.getStringAnnotations(tag = "URL" , start = offset , end = offset).firstOrNull()?.let {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.item))
                context.startActivity(intent)
            }



        }, modifier = modifier



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
                modifier = Modifier
                    .padding(top = 4.dp)
                    .clickable {
                        onReadMoreClicked()
                        expanded = true
                    }
            )
        }
    }


}