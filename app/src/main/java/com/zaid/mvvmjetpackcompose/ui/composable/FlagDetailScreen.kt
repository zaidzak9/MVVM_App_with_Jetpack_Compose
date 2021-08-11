package com.zaid.mvvmjetpackcompose.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltNavGraphViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.zaid.mvvmjetpackcompose.data.remote.responses.CountriesMainItem
import com.zaid.mvvmjetpackcompose.ui.CountryViewModel

@Composable
fun FlagDetailScreen(
    navController: NavController,
    flagName:String,
    viewModel: CountryViewModel = hiltViewModel()
) {
    viewModel.getCountry(flagName)
    val countryList by remember { viewModel.countriesList }
    Surface(
        color = MaterialTheme.colors.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            items(countryList.size) {
                    Text(text = countryList.get(0).name.toString())
                    Text(text = countryList.get(0).capital.toString())
                    Text(text = countryList.get(0).population.toString())
                    Text(text = countryList.get(0).currencies!!.get(0).name)
                    Text(text = countryList.get(0).languages!!.get(0).name)
                    Text(text = countryList.get(0).area.toString())
                    Text(text = countryList.get(0).callingCodes!!.get(0))
            }
        }
    }
}