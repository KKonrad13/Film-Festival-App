package com.example.filmfestivalapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmfestivalapp.repositories.FirestoreRepository
import com.example.filmfestivalapp.repositories.room.dao.MovieDao
import com.example.filmfestivalapp.repositories.room.dao.RatingDao
import kotlinx.coroutines.launch

class MainViewModel(
    private val firestore: FirestoreRepository,
    private val movieDao: MovieDao,
    private val ratingDao: RatingDao,
) : ViewModel() {
    fun init() {
        viewModelScope.launch {
            val movies = firestore.getMovies()
            movieDao.insertAll(movies.map { it.toEntity() })
            for (movie in movies) {
                ratingDao.insertAllIgnore(movie.ratings.map { it.toEntity(movie.id) })
            }
        }

    }
}