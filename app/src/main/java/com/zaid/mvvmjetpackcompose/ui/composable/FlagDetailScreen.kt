package com.zaid.mvvmjetpackcompose.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaid.mvvmjetpackcompose.data.remote.responses.CountriesMainItem
import com.zaid.mvvmjetpackcompose.ui.CountryViewModel
import com.zaid.mvvmjetpackcompose.ui.theme.Shapes

@Composable
fun FlagDetailScreen(
    navController: NavController,
    flagName: String,
    viewModel: CountryViewModel = hiltViewModel()
) {
    viewModel.getCountry(flagName)
    val countryList by remember { viewModel.countriesList }
    val isLoading by remember { viewModel.isloading }
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Green)
                .padding(bottom = 16.dp)
        ) {
            CountryDetailTopSection(
                navController = navController, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .align(Alignment.TopCenter)
            )
            LazyColumn(
                contentPadding = PaddingValues(16.dp), modifier = Modifier
                    .padding(20.dp)
                    .background(color =  MaterialTheme.colors.background)
                    .fillMaxHeight(0.9f)
                    .align(
                        Alignment.BottomStart
                    )
                    .fillMaxWidth()
            ) {
                items(countryList.size) {
                    Spacer(modifier = Modifier.height(80.dp))
                    CountryStat(countryList.get(0).name.toString(),"Name : ", Color.LightGray)
                    CountryStat(countryList.get(0).capital.toString(),"Capital : ",Color.Green)
                    CountryStat(countryList.get(0).population.toString(), "Population : ",Color.Blue)
                    CountryStat(countryList.get(0).currencies!!.get(0).name, "Currency : ", Color.Cyan)
                    CountryStat(countryList.get(0).languages!!.get(0).name,"Language: ", Color.Magenta)
                    CountryStat(countryList.get(0).area.toString(),"Area : ",Color.DarkGray)
                    CountryStat(countryList.get(0).callingCodes!!.get(0), "Calling Code : ",Color.Red)
                }
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                if(isLoading) {
                    CircularProgressIndicator(color = Color.Red)
                }
            }
        }
    }
}

@Composable
fun CountryDetailTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        Color.Black,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun CountryStat(
    stat: String,
    type: String,
    color :Color
) {
        Text(
            text = "$type $stat",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(35.dp)
                .clip(CircleShape)
                .background(color = color),
            color = Color.White,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
}
