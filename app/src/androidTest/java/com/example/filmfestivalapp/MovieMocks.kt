package com.example.filmfestivalapp

import com.example.filmfestivalapp.model.Movie
import com.example.filmfestivalapp.model.Rating
import java.time.LocalDateTime

object MovieMocks {
    val categories = listOf(
        R.string.acting,
        R.string.music,
        R.string.pictures,
        R.string.scenography,
        R.string.directors,
        R.string.costumes,
        R.string.screenwriting
    )
    val movie = Movie(
        id = "1",
        title = "Inception",
        date = LocalDateTime.of(2023, 11, 18, 16, 0),
        photo = "inception.jpg",
        place = "Hollywood",
        directors = listOf("Christopher Nolan"),
        screenwriters = listOf("Christopher Nolan"),
        genres = listOf("Sci-Fi", "Action"),
        producers = listOf("Emma Thomas", "Christopher Nolan"),
        ratings = categories.map {
            Rating(it, 2.0f)
         },
        note = "Awesome movie!",
        description = "Inception is a mind-bending science fiction film..."
    )


}