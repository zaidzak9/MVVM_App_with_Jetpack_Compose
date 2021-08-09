package com.zaid.mvvmjetpackcompose.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.zaid.mvvmjetpackcompose.R
import com.zaid.mvvmjetpackcompose.data.remote.responses.CountriesMainItem
import com.zaid.mvvmjetpackcompose.ui.CountryViewModel
import com.zaid.mvvmjetpackcompose.ui.theme.RobotoCondensed

@Composable
fun FlagListScreen(
    navController: NavController
) {
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(20.dp))
            Image(
                painter = painterResource(id = R.drawable.flag_app_logo),
                contentDescription = "Countries!",
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = CenterHorizontally)
            )

            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

            }
            Spacer(modifier = Modifier.height(16.dp))
            CountryList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }

    Box(modifier = modifier) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(4.dp, RoundedCornerShape(26.dp))
                .clip(AbsoluteRoundedCornerShape(20.dp)),
            value = text,
            placeholder = {
                Text(text = hint)
            },
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }

}


@Composable
fun CountryList(
    navController: NavController,
    viewModel: CountryViewModel = hiltViewModel()
) {
    val countryList by remember { viewModel.countriesList }
    val isLoading by remember { viewModel.isloading }

    println("JPC_UI - $countryList")

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if(countryList.size % 2 == 0) {
            countryList.size / 2
        } else {
            countryList.size / 2 + 1
        }
        items(itemCount) {
            FlagRow(rowIndex = it, entries = countryList, navController = navController)
        }
    }

    Box(
        contentAlignment = Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if(isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colors.primary)
        }
    }

}

@Composable
fun FlagIntro(
    countriesMainItem: CountriesMainItem,
    navController: NavController,
    modifier: Modifier = Modifier,
    countryViewModel: CountryViewModel = hiltViewModel()
) {
    val defaultDominantColor = MaterialTheme.colors.surface
    var dominantColor by remember {
        mutableStateOf(defaultDominantColor)
    }

    Box(
        contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        dominantColor,
                        defaultDominantColor
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "country_info_activity/${dominantColor.toArgb()}/${countriesMainItem.name}"
                )
            }
    ) {
        Column {
            Image(
                painter = rememberCoilPainter(request = countriesMainItem.flag),
                contentDescription = countriesMainItem.name,
                modifier = Modifier
                    .size(120.dp)
                    .align(CenterHorizontally)
            )
//            CoilImage(
//                request = ImageRequest.Builder(LocalContext.current)
//                    .data(countriesMainItem.flag)
//                    .target {
//                        countryViewModel.calcDominantColor(it) { color ->
//                            dominantColor = color
//                        }
//                    }
//                    .build(),
//                contentDescription = countriesMainItem.name,
//                fadeIn = true,
//                modifier = Modifier
//                    .size(120.dp)
//                    .align(CenterHorizontally)
//            ) {
//                CircularProgressIndicator(
//                    color = MaterialTheme.colors.primary,
//                    modifier = Modifier.scale(0.5f)
//                )
//            }
            Text(
                text = countriesMainItem.name!!,
                fontFamily = RobotoCondensed,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun FlagRow(
    rowIndex: Int,
    entries: List<CountriesMainItem>,
    navController: NavController
) {
    Column {
        Row {
            FlagIntro(
                countriesMainItem = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                FlagIntro(
                    countriesMainItem = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}