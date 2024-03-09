package com.example.filmfestivalapp.screens.movielist

import com.example.filmfestivalapp.OnClick
import com.example.filmfestivalapp.OnClick1
import com.example.filmfestivalapp.TextChange
import com.example.filmfestivalapp.model.Movie

interface MovieListUiInteraction {
    fun onBackClick()
    fun onSearchFilterChanged(filter: String)
    fun onMovieClick(movieId: String)
    fun onSortClick()
    fun onShowCategoriesClick()
    fun onCategoryClick(category: String)
    fun onCategoriesDialogDismiss()

    companion object {
        fun default(
            onBackClick: OnClick = {},
            onSearchFilterChanged: TextChange = {},
            onMovieClick: OnClick1<String> = {},
            onSortClick: OnClick = {},
            onShowCategoriesClick: OnClick = {},
            onCategoryClick: OnClick1<String> = {},
            onCategoriesDialogDismiss: ()->Unit
        ) = object : MovieListUiInteraction {
            override fun onBackClick() {
                onBackClick()
            }

            override fun onSearchFilterChanged(filter: String) {
                onSearchFilterChanged(filter)
            }

            override fun onMovieClick(movieId: String) {
                onMovieClick(movieId)
            }

            override fun onSortClick() {
                onSortClick()
            }

            override fun onShowCategoriesClick() {
                onShowCategoriesClick()
            }

            override fun onCategoryClick(category: String) {
                onCategoryClick(category)
            }

            override fun onCategoriesDialogDismiss() {
                onCategoriesDialogDismiss()
            }

        }
    }
}