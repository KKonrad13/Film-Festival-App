package com.example.filmfestivalapp.model

import androidx.annotation.StringRes
import com.example.filmfestivalapp.repositories.room.entity.RatingEntity

data class Rating(
    @StringRes
    val category: Int,
    val value: Float
){
    fun toEntity(movieId: String): RatingEntity{
        return RatingEntity(
            movieId = movieId,
            category = category,
            value = value
        )
    }
}