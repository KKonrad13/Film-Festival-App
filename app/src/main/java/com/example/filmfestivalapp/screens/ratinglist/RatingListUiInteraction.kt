package com.example.filmfestivalapp.screens.ratinglist

import com.example.filmfestivalapp.OnClick
import com.example.filmfestivalapp.OnClick1
import com.example.filmfestivalapp.TextChange
import com.example.filmfestivalapp.model.Movie

interface RatingListUiInteraction {
    fun onBackClick()
    fun onSearchFilterChanged(filter: String)
    fun onMovieClick(movieId: String)
    fun onSortClick(sortType: SortType)

    companion object {
        fun default(
            onBackClick: OnClick = {},
            onSearchFilterChanged: TextChange = {},
            onMovieClick: OnClick1<String> = {},
            onSortClick: OnClick1<SortType> = {},
        ) = object : RatingListUiInteraction {
            override fun onBackClick() {
                onBackClick()
            }

            override fun onSearchFilterChanged(filter: String) {
                onSearchFilterChanged(filter)
            }

            override fun onMovieClick(movieId: String) {
                onMovieClick(movieId)
            }

            override fun onSortClick(sortType: SortType) {
                onSortClick(sortType)
            }
        }
    }
}