package com.example.testwithpoetry.ui.main

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testwithpoetry.ui.main.viewmodels.MainViewModel
import com.example.testwithpoetry.ui.main.views.TabsScreen
import com.example.testwithpoetry.ui.main.views.WelcomeScreen
import com.example.testwithpoetry.ui.theme.TestWithPoetryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val mainViewModel: MainViewModel = hiltViewModel()
            val userInfoState by mainViewModel.userInfo.collectAsStateWithLifecycle()

            TestWithPoetryTheme {

                val navController = rememberNavController()
                Log.i("userInfo", userInfoState.user.toString())
                val startDestination = if (userInfoState.user != null) {
                    Routes.TabsScreen.route
                } else {
                    Routes.WelcomeScreen.route
                }

                NavHost(
                    navController = navController,
                    startDestination = startDestination
                ) {
                    composable(Routes.WelcomeScreen.route) {
                        WelcomeScreen(mainViewModel = mainViewModel, userInfoState = userInfoState)
                    }
                    composable(Routes.TabsScreen.route) {

                        TabsScreen(userInfoState = userInfoState)
                    }
                }


            }
        }
    }
}

