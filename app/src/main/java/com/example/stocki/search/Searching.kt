package com.example.stocki.search
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.material.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.stocki.data.pojos.TickerTypes
import androidx.compose.ui.Alignment
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.material3.SearchBar
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Searching( viewmodel: SearchViewmodel = hiltViewModel() ) {
    val state by viewmodel.state.collectAsState()

    var searchTextChange by remember {
        mutableStateOf("")
    }
    var active by rememberSaveable { mutableStateOf(false) }
    var filteredData by remember {
        mutableStateOf<List<TickerTypes>>(emptyList())
    }


    LaunchedEffect(searchTextChange) {
        // Fetch data only if searchTextChange is not empty
        if (searchTextChange.isNotEmpty()) {
            viewmodel.fetchAllData()
        }
    }
    when (val searchState = state) {
        is SearchState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is SearchState.Data -> {


             filteredData = searchState.data?.filter  { tickerList ->
                     tickerList.name?.contains(searchTextChange, ignoreCase = true) == true
                 } ?: emptyList()

            Log.d("StockiSearch", "SearchState filteredData ${filteredData}")

        }


        is SearchState.Error -> {
            Text(
                text = "Error: ${searchState.error}",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
            Log.d("StockiSearch", "SearchState.Error ${searchState.error}")

        }



    }
    Box(Modifier.fillMaxSize()){

            SearchBar(
                query = searchTextChange,
                onQueryChange = {  searchTextChange = it},
                onSearch = { active = false },
                active = active,
                onActiveChange = {
                    active = it
                },
                modifier = Modifier,
                content = {
                    LazyColumn {
                        items(filteredData) { ticker ->
                            Card(
                                modifier = Modifier
                                    .padding(10.dp)
                                    .fillMaxWidth()
                                    .clickable {

                                    }
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp)
                                ) {
                                    Text("name: ${ticker.name}")
                                    Text("ticker: ${ticker.ticker}")
                                    Text("locale: ${ticker.locale}")
                                }
                            }
                        }
                    }

                    /*TextField(
                        value = searchTextChange,
                        onValueChange = { searchTextChange = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Search") }
                    )*/}

            )
            /*LazyColumn {
                items(filteredData) { ticker ->
                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clickable {

                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text("name: ${ticker.name}")
                            Text("ticker: ${ticker.ticker}")
                            Text("locale: ${ticker.locale}")
                        }
                    }
                }
            }
*/
}
    /*  LaunchedEffect(Unit) {
        viewmodel.fetchData(listOf("stocks","crypto","otc","fx","indices"))
        Log.d("StockiSearch", "LaunchedEffect ")

    }*/

    /* when (val searchState = state) {
        is SearchState.Loading -> {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        }

        is SearchState.Data -> {
            val filteredData = searchState.data?.filter {
               // it.market.

          if (searchTextChange.isBlank()) {
              Text("No data available", modifier = Modifier.padding(16.dp))

              tickerTypes
            } else {
                tickerTypes.filter { it.name?.contains(searchTextChange, ignoreCase = true) == true }
            }

        }

            SearchView(
                modifier = Modifier,
                onSearchTextChanged = { text ->
                    searchTextChange = text
                    // onSearch(text)
                }
            )
            LazyColumn {
                items(filteredData) { tickerName ->

                    Card(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth()
                            .clickable {

                            }
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp)
                        ) {
                            Text("name: ${ticker.name}")
                            Text("ticker: ${ticker.ticker}")
                            Text("locale: ${ticker.locale}")
                        }
                    }
                }
            }

        }



        is SearchState.Error -> {
            Text(
                text = "Error: ${searchState.error}",
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
            Log.d("StockiSearch", "SearchState.Error ${searchState.error}")

        }



    }*/


}
/*@ExperimentalMaterial3Api
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    active: Boolean,
    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    shape: Shape = SearchBarDefaults.inputFieldShape,
    colors: SearchBarColors = SearchBarDefaults.colors(),
    tonalElevation: Dp = SearchBarDefaults.Elevation,
    windowInsets: WindowInsets = SearchBarDefaults.windowInsets,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable ColumnScope.() -> Unit,
){}*/

/*
    Surface(
        modifier = Modifier,
        color = Color.White,
        elevation = 8.dp

    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            /* Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = Color.Blue
            )
            Spacer(modifier = Modifier.width(2.dp))
            TextField(
                value = searchTextChange,
                onValueChange = {
                    searchTextChange = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = "Search") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )*/
        }*/
