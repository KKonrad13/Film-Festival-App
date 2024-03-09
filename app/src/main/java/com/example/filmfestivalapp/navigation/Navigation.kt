package com.example.filmfestivalapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.filmfestivalapp.screens.Screens
import com.example.filmfestivalapp.screens.home.HomeScreen
import com.example.filmfestivalapp.screens.moviedetails.MovieDetailsScreen
import com.example.filmfestivalapp.screens.movielist.MovieListScreen
import com.example.filmfestivalapp.screens.ratemovie.RateMovieScreen
import com.example.filmfestivalapp.screens.ratinglist.RatingListScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        modifier = modifier,
        startDestination = Screens.HOME.name
    ) {
        composable(route = Screens.HOME.name) {
            HomeScreen(
                onBackClick = navController::popBackStack,
                onMovieClick = { movieId -> navController.navigate("${Screens.MOVIE_DETAILS.name}/{$movieId}") },
                onMovieListClick = { navController.navigate(Screens.MOVIE_LIST.name) },
                onRatingListClick = { navController.navigate(Screens.RATING_LIST.name) }
            )
        }
        composable(
            route = "${Screens.MOVIE_DETAILS.name}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) {
            MovieDetailsScreen(
                onBackClick = navController::popBackStack,
                onStarsClick = { movieId -> navController.navigate("${Screens.RATE_MOVIE.name}/{$movieId}") },
                movieId = it.arguments?.getString("movieId")?.replace("[{}]".toRegex(),"")
            )
        }
        composable(route = Screens.MOVIE_LIST.name) {
            MovieListScreen(
                onBackClick = navController::popBackStack,
                onMovieClick = { movieId -> navController.navigate("${Screens.MOVIE_DETAILS.name}/{$movieId}") }
            )
        }
        composable(
            route = "${Screens.RATE_MOVIE.name}/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.StringType })
        ) {
            RateMovieScreen(
                onBackClick = { navController.popBackStack() },
                movieId = it.arguments?.getString("movieId")?.replace("[{}]".toRegex(),"")
            )
        }
        composable(route = Screens.RATING_LIST.name) {
            RatingListScreen(
                onBackClick = navController::popBackStack,
                onMovieClick = { movieId -> navController.navigate("${Screens.MOVIE_DETAILS.name}/{$movieId}") }
            )
        }
    }
}