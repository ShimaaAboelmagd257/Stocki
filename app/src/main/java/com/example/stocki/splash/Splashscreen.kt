package com.example.stocki.splash

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.example.stocki.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onAnimationFinished: () -> Unit,
    context:Context
) {
    val mediaPlayer = remember {
        MediaPlayer.create(context, R.raw.coinsound)
    }
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.moneycoins
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = 1,
        isPlaying = true
    )


    var animationStarted by remember { mutableStateOf(false) }
    var soundStarted by remember { mutableStateOf(false) }

    val modifierWithAnimationStart = if (animationStarted && !soundStarted) {
        soundStarted = true
        modifier.onGloballyPositioned {
            mediaPlayer.start()
        }
    } else {
        modifier
    }

    LaunchedEffect(Unit) {
      //  delay(10) // Adjust this delay if needed
        animationStarted = true
        delay(1650)
        mediaPlayer.stop()
        mediaPlayer.release()
        onAnimationFinished()
    }

    Box(
        modifier = modifierWithAnimationStart
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = preloaderLottieComposition,
            progress = preloaderProgress,
            modifier = Modifier.size(900.dp) // Adjust size as needed
        )
    }
}


