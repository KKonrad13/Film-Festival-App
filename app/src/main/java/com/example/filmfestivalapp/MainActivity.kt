package com.example.filmfestivalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.filmfestivalapp.navigation.AppNavHost
import com.example.filmfestivalapp.ui.theme.FilmFestivalAppTheme
import org.koin.androidx.compose.getViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel = getViewModel<MainViewModel>()
            LaunchedEffect(Unit){
                viewModel.init()
            }
            FilmFestivalAppTheme {
                AppNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = rememberNavController()
                )
            }
        }
    }
}
