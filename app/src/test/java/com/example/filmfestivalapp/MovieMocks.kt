package com.example.filmfestivalapp

import com.example.filmfestivalapp.repositories.room.entity.MovieEntity
import com.example.filmfestivalapp.repositories.room.entity.RatingEntity
import java.time.LocalDateTime
import java.time.ZoneOffset

object MovieMocks {

    val movieEntity1 = MovieEntity(
        id = "1",
        title = "Inception",
        date = LocalDateTime.of(2023, 11, 18, 16, 0).toEpochSecond(ZoneOffset.UTC),
        photo = "inception.jpg",
        place = "Hollywood",
        directors = listOf("Christopher Nolan"),
        screenwriters = listOf("Christopher Nolan"),
        genres = listOf("Sci-Fi", "Action"),
        producers = listOf("Emma Thomas", "Christopher Nolan"),
        note = "Awesome movie!",
        description = "Inception is a mind-bending science fiction film..."
    )

    val movieEntity2 = MovieEntity(
        id = "2",
        title = "The Shawshank Redemption",
        date = LocalDateTime.of(2023, 11, 18, 17, 0).toEpochSecond(ZoneOffset.UTC),
        photo = "shawshank_redemption.jpg",
        place = "Hollywood",
        directors = listOf("Frank Darabont"),
        screenwriters = listOf("Stephen King", "Frank Darabont"),
        genres = listOf("Drama"),
        producers = listOf("Niki Marvin"),
        note = "Classic!",
        description = "The Shawshank Redemption is a prison drama film..."
    )

    val movieEntity3 = MovieEntity(
        id = "3",
        title = "The Dark Knight",
        date = LocalDateTime.of(2023, 11, 18, 18, 0).toEpochSecond(ZoneOffset.UTC),
        photo = "dark_knight.jpg",
        place = "Hollywood",
        directors = listOf("Christopher Nolan"),
        screenwriters = listOf("Christopher Nolan", "Jonathan Nolan"),
        genres = listOf("Action", "Crime", "Drama"),
        producers = listOf("Emma Thomas", "Christopher Nolan", "Charles Roven"),
        note = "Why so serious?",
        description = "The Dark Knight is a superhero film based on the DC Comics character Batman..."
    )

    val allMovieEntities = listOf(movieEntity1, movieEntity2, movieEntity3)

    val categories = listOf(
        R.string.acting,
        R.string.music,
        R.string.pictures,
        R.string.scenography,
        R.string.directors,
        R.string.costumes,
        R.string.screenwriting
    )

    fun ratingEntities(movieId: String): List<RatingEntity> {
        return categories.map { category ->
            RatingEntity(
                movieId = movieId,
                category = category,
                value = 3.5f
            )
        }
    }

    val movie1 = movieEntity1.toMovie(emptyList())
    val movie2 = movieEntity2.toMovie(emptyList())
    val movie3 = movieEntity3.toMovie(emptyList())

    val allMoviesWithoutRatings = listOf(movie1, movie2, movie3)
}