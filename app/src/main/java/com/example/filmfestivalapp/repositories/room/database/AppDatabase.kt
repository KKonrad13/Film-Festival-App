package com.example.filmfestivalapp.repositories.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.filmfestivalapp.com.example.filmfestivalapp.repositories.room.Converters
import com.example.filmfestivalapp.repositories.room.dao.MovieDao
import com.example.filmfestivalapp.repositories.room.dao.RatingDao
import com.example.filmfestivalapp.repositories.room.entity.MovieEntity
import com.example.filmfestivalapp.repositories.room.entity.RatingEntity

@Database(entities = [MovieEntity::class, RatingEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun movieDaos() : MovieDao
    abstract fun ratingDaos() : RatingDao
    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "filmFestivalApp"
            ).build()
        }
    }
}