package com.example.filmfestivalapp.screens.ratemovie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmfestivalapp.model.Movie
import com.example.filmfestivalapp.model.Rating
import com.example.filmfestivalapp.repositories.room.dao.MovieDao
import com.example.filmfestivalapp.repositories.room.dao.RatingDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.ceil

class RateMovieViewModel(
    private val movieDao: MovieDao,
    private val ratingDao: RatingDao
): ViewModel() {
    private val _state = MutableStateFlow(RateMovieState())
    val state: StateFlow<RateMovieState> get() = _state

    fun init(movieId: String) {
        viewModelScope.launch {
            val movieEntity = movieDao.getMovieById(movieId) ?: return@launch
            val ratingEntities = ratingDao.getRatingById(movieId)
            _state.update {
                it.copy(movie = movieEntity.toMovie(
                    if (ratingEntities?.isNotEmpty() == true) {
                        ratingEntities.map { ratingEntity ->
                            if (ratingEntity.value >= 0){
                                ratingEntity.toRating()
                            }else{
                                ratingEntity.copy(value = 0.0f).toRating()
                            }
                        }
                    } else emptyList()
                ))
            }
        }
    }
    fun onConfirmClick() {
        viewModelScope.launch {
            ratingDao.insertAllReplace(_state.value.movie.ratings.map { it.toEntity(_state.value.movie.id) })
            movieDao.updateMovie(_state.value.movie.toEntity())
        }
    }

    fun onRatingChange(rating: Rating) {
        val updatedRating = rating.copy(value = ceil((rating.value * 2)).toInt().toFloat() / 2)
        val updatedRatings = _state.value.movie.ratings.map {
            if (it.category == rating.category){
                updatedRating
            }else{
                it
            }
        }
        _state.update {
            it.copy( movie = it.movie.copy(ratings = updatedRatings))
        }
    }

    fun onNotesChange(note: String) {
        _state.update {
            it.copy( movie = it.movie.copy(note = note))
        }
    }
}

data class RateMovieState(
    val movie: Movie = Movie()
)