package com.example.filmfestivalapp.screens.ratinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmfestivalapp.model.Movie
import com.example.filmfestivalapp.repositories.room.dao.MovieDao
import com.example.filmfestivalapp.repositories.room.dao.RatingDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RatingListViewModel(
    private val movieDao: MovieDao,
    private val ratingDao: RatingDao,
) : ViewModel() {
    private val _state = MutableStateFlow(RatingListState())
    val state: StateFlow<RatingListState> get() = _state

    init {
        _state.update {
            it.copy(
                sortOptions = listOf(
                    SortType.TITLE to SortMode.ASCENDING,
                    SortType.DATE to SortMode.NONE,
                    SortType.RATING to SortMode.NONE
                )
            )
        }
        getMovies()
    }

    fun onSearchFilterChanged(filter: String) {
        _state.update {
            it.copy(
                searchFilter = filter,
                shownMovies = it.allMovies.filter { movie -> movie.contains(filter) }
            )
        }
    }

    fun onSortClick(sortType: SortType) {
        var sortMode = SortMode.NONE
        _state.update {
            it.copy(
                sortOptions = it.sortOptions.map { sort ->
                    if (sort.first == sortType) {
                        sortMode = if (sort.second == SortMode.ASCENDING) {
                            SortMode.DESCENDING
                        } else {
                            SortMode.ASCENDING
                        }
                        sort.first to sortMode
                    } else {
                        sort.first to SortMode.NONE
                    }
                },
                shownMovies = when (sortType) {
                    SortType.TITLE -> {
                        if (sortMode == SortMode.ASCENDING) {
                            it.shownMovies.sortedBy { movie -> movie.title }
                        } else {
                            it.shownMovies.sortedByDescending { movie -> movie.title }
                        }
                    }

                    SortType.DATE -> {
                        if (sortMode == SortMode.ASCENDING) {
                            it.shownMovies.sortedBy { movie -> movie.date }
                        } else {
                            it.shownMovies.sortedByDescending { movie -> movie.date }
                        }
                    }

                    SortType.RATING -> {
                        if (sortMode == SortMode.ASCENDING) {
                            it.shownMovies.sortedBy { movie -> movie.movieRating }
                        } else {
                            it.shownMovies.sortedByDescending { movie -> movie.movieRating }
                        }
                    }
                }
            )
        }
    }

    private fun getMovies() {
        viewModelScope.launch {
            val movies = mutableListOf<Movie>()
            val moviesIds = ratingDao.getRatedMovies() ?: return@launch
            for (movieId in moviesIds) {
                val ratings = ratingDao.getRatingById(movieId)
                val movie = movieDao
                    .getMovieById(movieId)
                    ?.toMovie(
                        ratings = ratings?.map { it.toRating() } ?: emptyList()
                    )
                if (movie != null && movie.movieRating >= 0) {
                    movies.add(movie)
                }
            }
            _state.update {
                it.copy(
                    allMovies = movies,
                    shownMovies = movies
                )
            }
        }
    }
}

data class RatingListState(
    val allCategories: List<String> = emptyList(),
    val allMovies: List<Movie> = emptyList(),
    val shownMovies: List<Movie> = emptyList(),
    val searchFilter: String = "",
    val sortOptions: List<Pair<SortType, SortMode>> = emptyList(),
)

enum class SortType {
    TITLE, DATE, RATING
}

enum class SortMode {
    ASCENDING, DESCENDING, NONE
}