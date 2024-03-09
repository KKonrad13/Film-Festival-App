package com.example.filmfestivalapp

import com.example.filmfestivalapp.repositories.room.dao.MovieDao
import com.example.filmfestivalapp.screens.movielist.MovieListViewModel
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkStatic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime

class MovieListViewModelTest {
    private val movieDao: MovieDao = mockk {
        coEvery { this@mockk.getMovieById(any()) } returns MovieMocks.movieEntity1
        every { this@mockk.getAllMovies() } returns flowOf(MovieMocks.allMovieEntities)
    }
    private val viewModel = MovieListViewModel(
        movieDao = movieDao,
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns LocalDateTime.of(2023, 11, 18, 15, 0)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        unmockkStatic(LocalDateTime::class)
        Dispatchers.resetMain()
    }

    @Test
    fun `init adds movies and sets possible categories`() {
        viewModel.init()
        val movies = listOf(
            MovieMocks.movieEntity1,
            MovieMocks.movieEntity2,
            MovieMocks.movieEntity3
        )
        val categories = mutableListOf<String>()
        for (movie in movies) {
            for (genre in movie.genres) {
                if (genre.lowercase() !in categories) {
                    categories.add(genre.lowercase())
                }
            }
        }
        Truth.assertThat(viewModel.state.value.allCategories).isEqualTo(categories.toList().sortedBy { it })
        Truth.assertThat(viewModel.state.value.allMovies)
            .isEqualTo(movies.map { it.toMovie(emptyList()) })
        Truth.assertThat(viewModel.state.value.shownMovies)
            .isEqualTo(movies.map { it.toMovie(emptyList()) })
    }

    @Test
    fun `onTextFilterChanged sets filter in state and filters shownMovies but allMovies stays the same`(){
        viewModel.init()
        val textFilter = "InCePtIoN"
        viewModel.onSearchFilterChanged(textFilter)
        Truth.assertThat(viewModel.state.value.searchFilter).isEqualTo(textFilter)
        val allMovies = MovieMocks.allMoviesWithoutRatings
        val shownMovies = allMovies.filter { it.contains(textFilter) }
        Truth.assertThat(viewModel.state.value.shownMovies).isEqualTo(shownMovies)
        Truth.assertThat(viewModel.state.value.allMovies).isEqualTo(allMovies)
    }

    @Test
    fun `onCategoryClick adds or removes category whether its already in or not`(){
        val category1 = "drama"
        val category2 = "crime"
        viewModel.onCategoryClick(category1)
        Truth.assertThat(viewModel.state.value.chosenCategories).contains(category1)
        viewModel.onCategoryClick(category2)
        Truth.assertThat(viewModel.state.value.chosenCategories).contains(category2)
        viewModel.onCategoryClick(category1)
        Truth.assertThat(viewModel.state.value.chosenCategories).doesNotContain(category1)
        viewModel.onCategoryClick(category2)
        Truth.assertThat(viewModel.state.value.chosenCategories).doesNotContain(category2)
        Truth.assertThat(viewModel.state.value.chosenCategories).isEqualTo(emptyList<String>())
    }
}