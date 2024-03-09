package com.example.filmfestivalapp.screens.moviedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmfestivalapp.model.Movie
import com.example.filmfestivalapp.repositories.room.dao.MovieDao
import com.example.filmfestivalapp.repositories.room.dao.RatingDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailsViewModel(
    private val movieDao: MovieDao,
    private val ratingDao: RatingDao,
) : ViewModel() {
    private val _state = MutableStateFlow(MovieDetailsState())
    val state: StateFlow<MovieDetailsState> get() = _state

    fun init(movieId: String) {
        viewModelScope.launch {
            val movieEntity = movieDao.getMovieById(movieId) ?: return@launch
            val ratingEntities = ratingDao.getRatingById(movieId)
            _state.update {
                it.copy(movie = movieEntity.toMovie(
                    if (ratingEntities?.isNotEmpty() == true) {
                        ratingEntities.map { ratingEntity -> ratingEntity.toRating() }
                    } else emptyList()
                ))
            }
        }
    }
}

data class MovieDetailsState(
    val movie: Movie = Movie(),
)