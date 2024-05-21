package com.example.stocki.explore

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.stocki.utility.Constans
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.example.stocki.NavigationRoute
import com.example.stocki.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

data class CardContent(val title: String, val image : Painter , val id :Int , val route :String)

@Composable
fun getCardContentList(): List<CardContent> {
    return listOf(
        CardContent("What is stock market?", painterResource(id = R.drawable.marketexplore),1,
            NavigationRoute.ExploreMarket.route),
        CardContent("How to invest?", painterResource(id = R.drawable.buyorsell),3,NavigationRoute.ExploreTrade.route),
        CardContent("Market dividends", painterResource(id = R.drawable.dividends),6,NavigationRoute.ExploreDividends.route),
        CardContent("Stock Exchange", painterResource(id = R.drawable.img),4,NavigationRoute.ExploreExchange.route),
        CardContent("stock splits", painterResource(id = R.drawable.stocksplit),5,NavigationRoute.ExploreSplits.route),
        CardContent("Market types", painterResource(id = R.drawable.tradingmarkets),2,NavigationRoute.ExploreTypes.route),
        CardContent("Portfolio diversification", painterResource(id = R.drawable.stocki),7,NavigationRoute.ExploreTrade.route),
        CardContent("Understanding indices", painterResource(id = R.drawable.backo),8,NavigationRoute.ExploreTrade.route)
    )
}

@Composable
fun GridWithCards(onCardClicked: (route: String,it :Int) -> Unit) {
    val scrollState = rememberScrollState()

    val cardContentList = getCardContentList()
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {
        Column(modifier = Modifier
            .verticalScroll(scrollState)
            .padding(16.dp)) {
            repeat(4) { rowIndex ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    repeat(2) { columnIndex ->
                        val index = rowIndex * 2 + columnIndex
                        if (index < cardContentList.size) {
                            val content = cardContentList[index]
                        Card(
                            modifier = Modifier
                                .padding(8.dp)
                                .weight(1f)
                                .clickable {
                                    onCardClicked(content.route, content.id)

                                }
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(16.dp)

                            ) {
                                Text(
                                    text = content.title,
                                    color = Color.Black,
                                    style = MaterialTheme.typography.body1
                                )
                                Image(
                                    painter = content.image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(vertical = 20.dp)
                                        .size(200.dp)
                                        .padding(16.dp)
                                    ,
                                   // contentScale = ContentScale.Crop
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
}

/*
@Preview
@Composable
fun previewCards() {
    GridWithCards()
}
*/

@Composable
fun ExploreMarket(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            Text(text = "what is a stock market ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = "The stock market is like a bustling marketplace where investors buy and sell shares of publicly traded companies. \n Imagine you're in a huge treasure hunt game. Instead of looking for gold coins, you're searching for pieces of ownership in companies, like finding shiny gems.\n" +
                    "\n" +
                    "So, you start with a map showing all these different companies' treasures, and you decide which ones you want to dig for. Maybe you pick the 'Super Duper Toy Factory' because you love toys.\n" +
                    "\n" +
                    "You grab your shovel (or your phone) and start digging (or buying) for pieces of the Super Duper Toy Factory treasure. Now, here's the fun part – as more people want those treasures, the price goes up, like a treasure getting more valuable the closer you get to finding it.\n" +
                    "\n" +
                    "But sometimes, things happen that make people less interested in those treasures. Maybe the factory has a problem making toys, like if all the toy-making machines break. Then, the treasures aren't worth as much.\n" +
                    "\n" +
                    "So, you're always watching the map, looking for clues about which treasures might become more valuable or less valuable. It's like a big game of hide-and-seek, but instead of hiding, everyone's searching for the best treasures to own!\" \uD83D\uDDFA️\uD83D\uDCB0\uD83D\uDD0D",
                style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.stockMarketUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}
@Composable
fun ExploreDividends(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(100.dp)
        ) {
            Text(text = "what is a stock dividends ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = """
        Once upon a time, in a small town, there was a popular bakery called "Sweet Treats Bakery" owned by Mr. Baker. 
        The bakery was doing very well, and Mr. Baker decided to share his success with his loyal friends who had supported him from the beginning.
        
        To do this, Mr. Baker came up with the idea of selling tiny pieces of the bakery to his friends. These tiny pieces were called "shares." 
        So, if someone bought a share, they owned a small part of Sweet Treats Bakery.
        
        Every year, Sweet Treats Bakery made a lot of delicious cakes and earned a nice profit. Mr. Baker had a choice. 
        He could either keep all the profits for himself, or he could share some of the profits with his friends who owned shares. 
        Mr. Baker decided to share the profits. This sharing was called a "dividend."
        
        One year, instead of giving cash to his friends, Mr. Baker decided to do something different. 
        He baked a big, delicious cake and decided to give extra slices of this cake to his friends. 
        This meant that each friend who owned a share of the bakery would get more shares as a gift, instead of cash. 
        So, if a friend originally owned 1 share, they might get 1/10th of a share extra as a dividend.
        
        This extra piece of the bakery that each friend received was called a "stock dividend." 
        It didn't give them cash, but it increased the number of shares they owned in the bakery. 
        So, if the bakery continued to do well and grow, the value of their shares could increase over time.
        
        The friends were happy because their ownership in the bakery grew, and they believed in the future success of Sweet Treats Bakery. 
        This way, everyone shared in the bakery's growth and success.
        
        And that's the simple story of stock dividends: it's like getting extra pieces of the bakery (or company) as a reward for being an owner, instead of getting cash.
    """.trimIndent()
                ,
                style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.dividensUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}
@Composable
fun ExploreMarketTypes(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(60.dp)
        ) {
            Text(text = "what is a  market types ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text =  """
        In a bustling town, there was a large marketplace called "The Investment Bazaar." This marketplace was known for offering various types of investment opportunities to people looking to grow their wealth. Let’s meet some of the main attractions in this bazaar.
        
        1. 📈 Stocks Stall:
        The Stocks Stall was run by Mrs. Equity. She sold small pieces of companies called stocks. When people bought these stocks, they became part-owners of the companies and benefited if the companies did well.
        
        2. 📊 Indices Pavilion:
        Next to Mrs. Equity’s stall was the Indices Pavilion, managed by Mr. Index. He offered baskets of stocks called indices, representing the performance of a group of companies. Investing in an index was like owning a tiny part of all the companies in that index.
        
        3. 🌐 Crypto Corner:
        The Crypto Corner was a new and exciting place run by Ms. Digital. Here, you could buy digital currencies called cryptocurrencies, which were secured by blockchain technology. Popular cryptocurrencies included Bitcoin and Ethereum.
        
        4. 💱 Forex Booth:
        The Forex Booth was operated by Mr. Currency. Forex, or foreign exchange, involved trading different world currencies. People bought one currency and sold another, hoping to profit from changes in exchange rates.
        
        5. 📉 Options Stand:
        Finally, there was the Options Stand, run by Mrs. Flex. She sold options, which were contracts giving the right to buy or sell assets at a set price by a certain date. Options allowed for bets on future price movements with high potential rewards but also high risks.
    """.trimIndent(),
                style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.typesUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}
@Composable
fun ExploreSplits(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current, id: Int =5) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(60.dp)
        ) {
            Text(text = "what is a  market splits ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = """
        📉  Stock Splits

        In a small town, there was a popular bakery called "Sweet Treats Bakery" owned by Mr. Baker. The bakery was doing so well that the price of its shares, which people could buy to own a part of the bakery, kept increasing.

        One day, the shares became very expensive, making it hard for new friends to buy them. To solve this, Mr. Baker decided to do a stock split. He announced that for every one share people owned, they would now have two shares, each worth half as much as before.

        Imagine it like this: If someone had one big slice of cake, Mr. Baker cut it into two smaller slices. The total amount of cake was still the same, but now there were more slices, making it easier for more people to afford a piece.

        The value of each friend's total shares remained the same, but the number of shares they owned doubled. This made it easier for new friends to buy shares and join in the success of Sweet Treats Bakery.

        And that’s the simple story of stock splits: it’s like cutting a big piece of cake into smaller pieces so more people can enjoy a slice without changing the total amount of cake.
    """.trimIndent()
                ,style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.splitsUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}
@Composable
fun ExploreBuyAndSell(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current, id: Int = 3) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(80.dp)
        ) {
            Text(text = "When to buy and sell  ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text = """
        📅 Story: The Wise Trader's Timing - When to Buy and Sell

        In the bustling town of Investville, there lived a wise trader named Mr. Sage. Mr. Sage loved to share his knowledge about the best times to buy and sell investments at the Investment Bazaar.

        **Buying Time:**
        Mr. Sage advised that the best time to buy is when the prices are low, and there's potential for growth. He used to say, "Imagine buying fruits when they are in season and cheap. Later, when the demand increases, their prices go up, and you have made a good deal." Similarly, buying stocks, crypto, or other assets when their prices are undervalued and the company's or asset's future looks promising is a good strategy.

        **Selling Time:**
        On the other hand, Mr. Sage recommended selling when the prices are high and you've made a good profit. He compared it to selling a rare, valuable collectible at an auction when there are many eager buyers. If the investment's price has gone up significantly and there are signs that it might not rise much further, it might be a good time to sell and lock in the profits.

        **Stay Informed:**
        Mr. Sage also emphasized staying informed about market news, trends, and the performance of the investments. "Just like a gardener watches the weather to know when to plant and harvest," he said, "a trader must watch the market to know the best times to buy and sell."

        And so, the wise people of Investville followed Mr. Sage's advice, buying low and selling high, while always staying informed. They made wise trading decisions and prospered in the Investment Bazaar.

        And that’s the simple story of when to trade: buy when prices are low with potential for growth, sell when prices are high to secure profits, and stay informed about the market.
    """.trimIndent()
               , style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.buy_sellUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}


@Composable
fun ExploreMarketExchange(lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {
    val scrollState = rememberScrollState()
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(80.dp)
        ) {
            Text(text = " market Exchange ? \uD83D\uDCC8", style = MaterialTheme.typography.h5)
            Image(
                painter = painterResource(id = R.drawable.marketexplore),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )
            Text(text =
            """
        🏛️ Story: The Busy Marketplace - What is a Stock Exchange?

        In the heart of the financial district, there stood a magnificent building known as the Stock Exchange. It was like a bustling marketplace where people traded stocks, bonds, and other securities.

        **Trading Floor:**
        Inside the Stock Exchange, there was a vibrant trading floor where traders, brokers, and investors gathered. They shouted out orders to buy and sell stocks, creating a lively atmosphere filled with excitement and anticipation.

        **Matching Buyers and Sellers:**
        The main role of the Stock Exchange was to match buyers with sellers. When someone wanted to buy a stock, there had to be someone else willing to sell it at the same price, and vice versa. The Stock Exchange facilitated this process, ensuring fair and orderly trading.

        **Setting Prices:**
        Another important function of the Stock Exchange was to set prices for securities based on supply and demand. When there were more buyers than sellers, prices went up, and when there were more sellers than buyers, prices went down. These price movements reflected the market's perception of a company's value.

        **Regulation and Oversight:**
        The Stock Exchange was also responsible for regulating and overseeing the market to ensure fairness, transparency, and integrity. Rules and regulations were in place to prevent fraud, manipulation, and insider trading, maintaining the trust and confidence of investors.

        **Global Connectivity:**
        In today's interconnected world, many stock exchanges are linked together electronically, allowing investors from around the globe to trade seamlessly. This global connectivity provides opportunities for diversification and access to a wide range of investment options.

        And so, the Stock Exchange served as the cornerstone of the financial markets, providing a platform for investors to buy and sell securities, set prices, and ensure fair and orderly trading.

        And that’s the simple story of a stock exchange: a bustling marketplace where buyers and sellers trade stocks and other securities, with rules and regulations to ensure fairness and integrity.
    """.trimIndent()
                ,style = MaterialTheme.typography.body1)
            YouTubeVideoView(Constans.stockExchangeUrl, lifecycleOwner = lifecycleOwner)


        }
    }

}
@Composable
fun YouTubeVideoView(videoUrl: String, lifecycleOwner: LifecycleOwner) {
    Log.d("YouTubeVideoView", "Loading video URL: $videoUrl")
    val context = LocalContext.current
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            // .aspectRatio(16 / 9f)
            .clip(RoundedCornerShape(3.dp))
            .background(Color.Black),factory ={ YouTubePlayerView(context = context ).apply {
        lifecycleOwner.lifecycle.addObserver(this)
        addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                youTubePlayer.loadVideo(videoUrl, 0f)
                //  super.onReady(youTubePlayer)
            }
        })
        }
        })

}

