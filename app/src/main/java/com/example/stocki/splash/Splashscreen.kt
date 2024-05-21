package com.example.stocki.splash

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import com.example.stocki.R
import kotlinx.coroutines.delay
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.stocki.LocalSharedPreferences
import com.example.stocki.NavigationRoute
import com.example.stocki.utility.Constans


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    onAnimationFinished: () -> Unit,
    context:Context,
    navController :NavController
) {
    val sharedPreferences = LocalSharedPreferences.current
    val savedSignIn = sharedPreferences.getBoolean(Constans.SAVED_SIGNIN, false)
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
    // Navigate to the appropriate destination based on the sign-in state
    LaunchedEffect(savedSignIn) {
        delay(1650)
        if (savedSignIn) {
            navController.navigate(NavigationRoute.Main.route)
        } else {
            navController.navigate(NavigationRoute.Entrance.route)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // Center text horizontally
    ) {
       // Spacer(modifier = Modifier.height(200.dp))
        Text(
            text = "Stocki",
            color = Color.Red,
            fontSize = 80.sp,
            fontFamily = FontFamily( Font(R.font.aclonica)),
                    modifier = Modifier.padding(bottom = 80.dp)
        )
            LottieAnimation(
                composition = preloaderLottieComposition,
                progress = preloaderProgress,
                modifier = modifierWithAnimationStart
            )
    }

}


