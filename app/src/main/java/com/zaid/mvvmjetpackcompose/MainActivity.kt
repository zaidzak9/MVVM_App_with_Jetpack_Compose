package com.zaid.mvvmjetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.zaid.mvvmjetpackcompose.ui.composable.FlagListScreen
import com.zaid.mvvmjetpackcompose.ui.theme.MVVMAppWithJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMAppWithJetpackComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "country_list_activity") {
                    composable("country_list_activity") {
                        FlagListScreen(navController = navController)
                    }
                    composable("country_info_activity/{flagName}/{dominantColor}", arguments = listOf(
                        navArgument("flagName") {
                            type = NavType.StringType
                        },
                        navArgument("dominantColor") {
                            type = NavType.IntType
                        }
                    )) {
                        val flagName = remember {
                            it.arguments?.getString("flagName")
                        }

                        val dominantColor = remember {
                            val color = it.arguments?.getInt("dominantColor")
                            color?.let {Color(it)}?:Color.White
                        }
                    }
                }

            }
        }
    }
}

