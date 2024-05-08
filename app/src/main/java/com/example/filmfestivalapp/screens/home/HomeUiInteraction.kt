package com.example.filmfestivalapp.screens.home

import com.example.filmfestivalapp.Navigation
import com.example.filmfestivalapp.OnClick
import com.example.filmfestivalapp.OnClick1

interface HomeUiInteraction {
    fun onBackClick()
    fun onFacebookClick()
    fun onInstagramClick()
    fun onYoutubeClick()
    fun onMovieClick(movieId: String)
    fun onMovieListClick()
    fun onRatingListClick()
    fun onPromoMovieClick()

    companion object {
        fun default(
            onBackClick: Navigation = {},
            onFacebookClick: OnClick = {},
            onInstagramClick: OnClick = {},
            onYoutubeClick: OnClick = {},
            onMovieClick: OnClick1<String> = {},
            onMovieListClick: Navigation = {},
            onRatingListClick: Navigation = {},
            onPromoMovieClick: Navigation = {}
        ) = object : HomeUiInteraction {
            override fun onBackClick() {
                onBackClick()
            }

            override fun onFacebookClick() {
                onFacebookClick()
            }

            override fun onInstagramClick() {
                onInstagramClick()
            }

            override fun onYoutubeClick() {
                onYoutubeClick()
            }

            override fun onMovieClick(movieId: String) {
                onMovieClick(movieId)
            }

            override fun onMovieListClick() {
                onMovieListClick()
            }

            override fun onRatingListClick() {
                onRatingListClick()
            }

            override fun onPromoMovieClick() {
                onPromoMovieClick()
            }

        }
    }
}