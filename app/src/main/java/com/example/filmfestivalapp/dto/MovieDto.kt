package com.example.filmfestivalapp.dto

import com.example.filmfestivalapp.R
import com.example.filmfestivalapp.model.Movie
import com.example.filmfestivalapp.model.Rating
import java.time.ZoneId

val RATING_CATEGORIES = listOf(
    R.string.acting,
    R.string.music,
    R.string.pictures,
    R.string.scenography,
    R.string.directors,
    R.string.costumes,
    R.string.screenwriting
)

data class MovieDto(
    val id: String = "",
    val title: String = "",
    val date: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now(),
    val photo: String = "",
    val place: String = "",
    val directors: List<String> = emptyList(),
    val screenwriters: List<String> = emptyList(),
    val genres: List<String> = emptyList(),
    val producers: List<String> = emptyList(),
    val ratings: List<String> = emptyList(),
    val note: String = "",
    val description: String = "",
) {
    fun toMovie(): Movie {
        val convertedDate = date
            .toDate()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()

        return Movie(
            id = id,
            title = title.lowercase(),
            date = convertedDate,
            photo = photo,
            place = place,
            directors = directors,
            screenwriters = screenwriters,
            genres = genres.map { it.lowercase() },
            producers = producers,
            ratings = RATING_CATEGORIES.map { Rating(it, -1.0f) },
            note = "",
            description = description
        )
    }
}
