package com.example.filmfestivalapp.repositories.room.entity

import androidx.room.Entity
import com.example.filmfestivalapp.model.Rating

@Entity(tableName = "ratings", primaryKeys = ["movieId", "category"])
data class RatingEntity(
    val movieId: String,
    val category: Int,
    val value: Float
) {
    fun toRating(): Rating {
        return Rating(
            category = category,
            value = value
        )
    }
}