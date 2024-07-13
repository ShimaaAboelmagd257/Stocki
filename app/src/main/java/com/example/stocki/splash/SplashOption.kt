package com.example.stocki.splash

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.stocki.R

data class IntroItem(
    val img:Int,
    val desc:String
)
val introItem = listOf(
    IntroItem(R.drawable.shutterstock,"Get access to updated stock market prices  "),
    IntroItem(R.drawable.cash," practise trading with $1000.00 virtual cash "),
    IntroItem(R.drawable.time,"Spend your time learning and invest  ")

)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun IntroItemView(introItem: IntroItem, pagerState: PagerState, totalItems: Int) {
   // Card() {


    Column(
        modifier = Modifier
           .fillMaxSize()
           // .padding(16.dp),
      //  verticalArrangement = Arrangement.Center,
       // horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = introItem.img) ,
            contentDescription = null ,
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .clip(RoundedCornerShape(topStart = 0.dp, topEnd = 5.dp)),
            contentScale = ContentScale.FillBounds

        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = introItem.desc,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            repeat(totalItems) { index ->
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .drawIndicator(pagerState, index)
                        //.padding(4.dp)
                )
            }
        }
    }
}
   // }


//@OptIn(ExperimentalPagerApi::class)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SplashScreen(introItems: List<IntroItem> ,onButtonClick:()->Unit) {
    val pagerState = rememberPagerState(initialPage = 0, initialPageOffsetFraction = 0f, pageCount = { introItems.size })

    Column(modifier = Modifier
        .fillMaxSize()
        .background(color = Color.White)
        ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)

        ) { page ->
            IntroItemView(introItem = introItems[page], pagerState = pagerState, totalItems = introItems.size)
        }
        Spacer(modifier = Modifier.height(40.dp))
      //  introItems.forEachIndexed { index, _ ->

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding( 16.dp),
                  //  .align(Alignment.CenterHorizontally),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ){
               /* introItems.forEachIndexed { index, _ ->
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .drawIndicator(pagerState, index).padding(4.dp)
                    )
                }*/
                Spacer(modifier = Modifier.height(16.dp))
                if (pagerState.currentPage == introItems.size - 1) {
                    Button(
                        onClick = { onButtonClick() },
                        modifier = Modifier
                            .padding(bottom = 16.dp).fillMaxWidth()
                    ) {
                        Text("Get Started")
                    }
                }
            }

      //  }

    }
}