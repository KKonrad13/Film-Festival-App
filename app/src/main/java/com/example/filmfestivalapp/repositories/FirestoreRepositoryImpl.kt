package com.example.filmfestivalapp.repositories

import android.util.Log
import com.example.filmfestivalapp.dto.MovieDto
import com.example.filmfestivalapp.model.Movie
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

const val MOVIES_PATH = "/Movie"
const val ERROR = "ERROR"

class FirestoreRepositoryImpl(
    private val firestore: FirebaseFirestore,
) : FirestoreRepository {
    override suspend fun getMovies(): List<Movie> {
        return try {
            firestore.collection(MOVIES_PATH).get().await().map {
                it.toObject(MovieDto::class.java).toMovie()
            }
        } catch (e: Exception) {
            Log.e(ERROR, "getMovies: $e")
            emptyList()
        }
    }

}