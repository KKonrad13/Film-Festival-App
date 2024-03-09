package com.example.filmfestivalapp.com.example.filmfestivalapp.repositories.room

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun stringListToString(list: List<String>): String{
        return list.joinToString(";")
    }

    @TypeConverter
    fun stringToStringList(string: String): List<String>{
        return string.split(";")
    }
}