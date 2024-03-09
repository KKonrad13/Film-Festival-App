package com.example.filmfestivalapp.repositories

import com.example.filmfestivalapp.model.Movie

interface FirestoreRepository {
    suspend fun getMovies(): List<Movie>
}