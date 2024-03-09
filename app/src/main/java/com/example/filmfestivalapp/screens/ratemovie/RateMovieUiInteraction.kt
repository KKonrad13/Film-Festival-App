package com.example.filmfestivalapp.screens.ratemovie

import com.example.filmfestivalapp.Navigation
import com.example.filmfestivalapp.OnClick
import com.example.filmfestivalapp.TextChange
import com.example.filmfestivalapp.model.Rating

interface RateMovieUiInteraction {
    fun onBackClick()
    fun onConfirmClick()
    fun onRatingChange(rating: Rating)
    fun onNotesChange(note: String)

    companion object {
        fun default(
            onBackClick: Navigation = {},
            onConfirmClick: OnClick = {},
            onRatingChange: (Rating) -> Unit = {},
            onNotesChange: TextChange = {},
        ) = object : RateMovieUiInteraction {
            override fun onBackClick() {
                onBackClick()
            }

            override fun onConfirmClick() {
                onConfirmClick()
            }

            override fun onRatingChange(rating: Rating) {
                onRatingChange(rating)
            }

            override fun onNotesChange(note: String) {
                onNotesChange(note)
            }
        }
    }
}