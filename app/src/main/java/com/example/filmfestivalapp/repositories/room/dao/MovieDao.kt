package com.example.filmfestivalapp.repositories.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.filmfestivalapp.repositories.room.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * FROM movies WHERE id = :movieId")
    suspend fun getMovieById(movieId: String): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(movieEntities: List<MovieEntity>)

    @Update
    suspend fun updateMovie(movieEntity: MovieEntity)

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieEntity>>
}