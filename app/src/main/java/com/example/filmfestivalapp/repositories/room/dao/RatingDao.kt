package com.example.filmfestivalapp.repositories.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.filmfestivalapp.repositories.room.entity.RatingEntity

@Dao
interface RatingDao {
    @Query("SELECT * FROM ratings WHERE movieId = :movieId")
    suspend fun getRatingById(movieId: String): List<RatingEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllReplace(ratings: List<RatingEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIgnore(ratings: List<RatingEntity>)
    @Query("SELECT DISTINCT movieId FROM ratings")
    suspend fun getRatedMovies(): List<String>?

}