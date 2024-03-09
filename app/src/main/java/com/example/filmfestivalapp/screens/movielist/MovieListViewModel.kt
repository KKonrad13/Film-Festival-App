package com.example.filmfestivalapp.screens.movielist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmfestivalapp.model.Movie
import com.example.filmfestivalapp.repositories.room.dao.MovieDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val movieDao: MovieDao,
) : ViewModel() {
    private val _state = MutableStateFlow(MovieListState())
    val state: StateFlow<MovieListState> get() = _state.asStateFlow()

    fun init() {
        viewModelScope.launch {
            val movies = mutableListOf<Movie>()
            movieDao.getAllMovies().collect { allMoviesEntities ->
                for (movieEntity in allMoviesEntities) {
                    movies.add(movieEntity.toMovie(ratings = emptyList()))
                }
                val categories = mutableListOf<String>()
                for (movie in movies) {
                    for (genre in movie.genres) {
                        if (genre.lowercase() !in categories) {
                            categories.add(genre.lowercase())
                        }
                    }
                }
                _state.update { state ->
                    state.copy(
                        allMovies = movies.sortedBy { it.date },
                        shownMovies = movies.sortedBy { it.date },
                        allCategories = categories.sortedBy { it }
                    )
                }
            }


        }
    }

    fun onSearchFilterChanged(filter: String) {
        _state.update {
            it.copy(
                searchFilter = filter,
                shownMovies = it.allMovies.filter { movie -> movie.contains(filter) }
            )
        }
    }

    fun onSortClick() {
        _state.update {
            it.copy(
                ascendingSort = !it.ascendingSort,
                shownMovies = if (it.ascendingSort) {
                    it.shownMovies.sortedByDescending { movie -> movie.date }
                } else {
                    it.shownMovies.sortedBy { movie -> movie.date }
                }
            )
        }
    }

    fun onShowCategoriesClick() {
        if (_state.value.allCategories.isNotEmpty()) {
            _state.update { it.copy(isCategoriesDialogShown = true) }
        }
    }

    fun onCategoryClick(category: String) {
        val updatedChosenCategories = _state.value.chosenCategories.toMutableList()
        if (category in updatedChosenCategories) {
            updatedChosenCategories.remove(category)
        } else {
            updatedChosenCategories.add(category)
        }
        _state.update {
            it.copy(
                chosenCategories = updatedChosenCategories,
                shownMovies = it.allMovies
                    .filter { movie ->
                        movie.contains(it.searchFilter)
                    }
                    .filter { movie ->
                        movie.genres.any { genre ->
                            if (updatedChosenCategories.isEmpty()) {
                                true
                            } else {
                                genre in updatedChosenCategories
                            }
                        }
                    }
            )
        }
    }

    fun onCategoriesDialogDismiss() {
        _state.update { it.copy(isCategoriesDialogShown = false) }
    }


}

data class MovieListState(
    val allCategories: List<String> = emptyList(),
    val allMovies: List<Movie> = emptyList(),
    val shownMovies: List<Movie> = emptyList(),
    val searchFilter: String = "",
    val ascendingSort: Boolean = true,
    val isCategoriesDialogShown: Boolean = false,
    val chosenCategories: List<String> = emptyList(),
)