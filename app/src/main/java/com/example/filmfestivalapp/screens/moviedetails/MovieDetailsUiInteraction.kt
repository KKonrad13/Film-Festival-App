package com.example.filmfestivalapp.screens.moviedetails

import com.example.filmfestivalapp.Navigation
import com.example.filmfestivalapp.Navigation1
import com.example.filmfestivalapp.OnClick

interface MovieDetailsUiInteraction {
    fun onBackClick()
    fun onStarsClick(movieId: String)

    companion object {
        fun default(
            onBackClick: Navigation = {},
            onStarsClick: Navigation1<String> = {}
        ) = object : MovieDetailsUiInteraction {
            override fun onBackClick() {
                onBackClick()
            }

            override fun onStarsClick(movieId: String) {
                onStarsClick(movieId)
            }

        }
    }
}