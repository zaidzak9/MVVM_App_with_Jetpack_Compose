package com.zaid.mvvmjetpackcompose

import android.os.Bundle
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController
import com.zaid.mvvmjetpackcompose.ui.composable.FlagDetailScreen
import com.zaid.mvvmjetpackcompose.ui.composable.FlagListScreen
import com.zaid.mvvmjetpackcompose.ui.theme.MVVMAppWithJetpackComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVMAppWithJetpackComposeTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "splash_screen") {
                    composable("splash_screen"){
                        SplashScreen(navController = navController)
                    }
                    composable("country_list_activity") {
                        FlagListScreen(navController = navController)
                    }
                    composable("country_info_activity/{flagName}", arguments = listOf(
                        navArgument("flagName") {
                            type = NavType.StringType
                        }
                    )) {
                        val flagName = remember {
                            it.arguments?.getString("flagName")
                        }
                        FlagDetailScreen(navController = navController, flagName = flagName ?: "")
                    }
                }

            }
        }
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(2000L)
        navController.navigate("country_list_activity")
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = R.drawable.flag_app_logo),
            contentDescription = "Fun With Flags!"
        )
    }
}

