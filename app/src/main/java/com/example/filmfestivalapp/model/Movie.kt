package com.example.filmfestivalapp.model

import com.example.filmfestivalapp.repositories.room.entity.MovieEntity
import com.example.filmfestivalapp.screens.components.capitalizeFirstLetter
import java.time.LocalDateTime
import java.time.ZoneOffset


data class Movie(
    val id: String = "",
    val title: String = "",
    val date: LocalDateTime = LocalDateTime.now(),
    val photo: String = "",
    val place: String = "",
    val directors: List<String> = emptyList(),
    val screenwriters: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val producers: List<String> = emptyList(),
    val ratings: List<Rating> = emptyList(),
    val note: String = "",
    val description: String = ""
) {
    val movieRating = ratings.sumOf { it.value.toDouble() }.toFloat() / ratings.size
    val displayDirectors = directors.joinToString(", ")
    val displayScreenwriters = screenwriters.joinToString(", ")
    val displayGenres = genres.joinToString(", ") { it.capitalizeFirstLetter() }
    val displayProducers = producers.joinToString(", ")

    fun contains(text: String): Boolean = title.lowercase().contains(text.lowercase()) ||
            date.toString().lowercase().contains(text.lowercase()) ||
            place.lowercase().contains(text.lowercase()) ||
            directors.any { it.lowercase().contains(text.lowercase()) } ||
            screenwriters.any { it.lowercase().contains(text.lowercase()) } ||
            genres.any { it.lowercase().contains(text.lowercase()) } ||
            producers.any { it.lowercase().contains(text.lowercase()) } ||
            ratings.any { it.toString().lowercase().contains(text.lowercase()) }

    fun toEntity(): MovieEntity {
        return MovieEntity(
            id = id,
            title = title,
            date = date.toEpochSecond(ZoneOffset.UTC),
            photo = photo,
            place = place,
            directors = directors,
            screenwriters = screenwriters,
            genres = genres,
            producers = producers,
            note = note,
            description = description
        )
    }
}
