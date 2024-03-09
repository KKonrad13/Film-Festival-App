package com.example.filmfestivalapp.repositories.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.filmfestivalapp.model.Movie
import com.example.filmfestivalapp.model.Rating
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    val date: Long,
    val photo: String,
    val place: String,
    val directors: List<String>,
    val screenwriters: List<String>,
    val genres: List<String>,
    val producers: List<String>,
    val note: String,
    val description: String
){
    fun toMovie(ratings: List<Rating>): Movie {
        val instant = Instant.ofEpochSecond(date)
        val convertedDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        return Movie(
            id = id,
            title = title,
            date = convertedDate,
            photo = photo,
            place = place,
            directors = directors,
            screenwriters = screenwriters,
            genres = genres,
            producers = producers,
            ratings = ratings,
            note = note,
            description = description
        )
    }
}