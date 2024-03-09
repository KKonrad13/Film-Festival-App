package com.example.filmfestivalapp

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.filmfestivalapp.model.Rating
import com.example.filmfestivalapp.screens.ratemovie.RateMovieScreenContent
import com.example.filmfestivalapp.screens.ratemovie.RateMovieState
import com.example.filmfestivalapp.screens.ratemovie.RateMovieUiInteraction
import com.example.filmfestivalapp.screens.ratemovie.RateSection
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RateMovieScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testRateMovieScreen() {
        val initialState = RateMovieState(movie = MovieMocks.movie)

        composeTestRule.setContent {
            RateMovieScreenContent(
                state = initialState,
                uiInteraction = RateMovieUiInteraction.default()
            )
        }
        //check existence of title of the movie on the screen
        composeTestRule.onNodeWithText(
            text = initialState.movie.title,
            ignoreCase = true,
            useUnmergedTree = true).assertExists()
        //check displayed movie rating
        composeTestRule.onNodeWithText(
            ((initialState.movie.movieRating * 100).toInt() / 100.0)
                .toString() + "/5"
        ).assertExists()
        //check display of each movie genre
        for (genre in initialState.movie.genres){
            composeTestRule.onNodeWithText(text = genre, ignoreCase = true).assertExists()
        }

    }

    @Test
    fun rateSectionShowsStarsAndRatingCategory() {
        composeTestRule.setContent {
            var rating = Rating(category = R.string.acting, value = 3.5f)
            RateSection(rating = rating, onRateChange = { rating = it })
        }
        composeTestRule.onNodeWithText("Gra aktorska").assertExists()
        composeTestRule.onNodeWithTag(testTag = "star0", useUnmergedTree = true)
            .assertExists()
        composeTestRule.onNodeWithTag(testTag = "star1", useUnmergedTree = true)
            .assertExists()
        composeTestRule.onNodeWithTag(testTag = "star2", useUnmergedTree = true)
            .assertExists()
        composeTestRule.onNodeWithTag(testTag = "star3", useUnmergedTree = true)
            .assertExists()
        composeTestRule.onNodeWithTag(testTag = "star4", useUnmergedTree = true)
            .assertExists()
    }
}