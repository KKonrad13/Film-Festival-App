package com.example.filmfestivalapp
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.filmfestivalapp.navigation.AppNavHost
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testNavigationFromHomeToMovieListAndBack() {
        composeTestRule.setContent {
            AppNavHost(navController = rememberNavController())
        }
        //Button in HomeScreen - to go to MovieListScreen
        composeTestRule.onNodeWithText("Wyświetlane filmy").performClick()
        //OutlinedTextField in MovieListScreen - to check if we're in MovieListScreen
        composeTestRule
            .onNodeWithTag("findMovie")
            .assertExists()
        //Icon back in MovieListScreen - to go back to HomeScreen
        composeTestRule
            .onNodeWithTag("iconBackMovieList")
            .performClick()
        //Button in HomeScreen - to check if we're back in HomeScreen
        composeTestRule
            .onNodeWithText("Wyświetlane filmy")
            .assertExists()
        //Button in HomeScreen - to check if we're back in HomeScreen
        composeTestRule
            .onNodeWithText("Twoje oceny")
            .assertExists()
    }
}
