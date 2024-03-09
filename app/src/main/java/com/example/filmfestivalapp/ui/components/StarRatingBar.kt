package com.example.filmfestivalapp.screens.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import com.example.filmfestivalapp.OnClick
import com.example.filmfestivalapp.R
import com.example.filmfestivalapp.ui.Dimensions


@Composable
fun StarRatingBar(
    rating: Float,
    modifier: Modifier = Modifier,
    starSize: Dp = Dimensions.icon48,
    onClick: OnClick = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End,
        modifier = modifier
            .clickableNoIndication { onClick() }
            .testTag("starRatingBar")
    ) {
        Star(rating = rating, index = 0, starSize = starSize)
        Star(rating = rating, index = 1, starSize = starSize)
        Star(rating = rating, index = 2, starSize = starSize)
        Star(rating = rating, index = 3, starSize = starSize)
        Star(rating = rating, index = 4, starSize = starSize)
    }
}

@Composable
fun Star(
    rating: Float,
    index: Int,
    starSize: Dp
) {
    Icon(
        painter = painterResource(
            id = if (rating < index + 0.5) R.drawable.icon_star_empty
            else if (rating < index + 1.0) R.drawable.icon_star_half
            else R.drawable.icon_star
        ),
        contentDescription = null,
        modifier = Modifier
            .size(starSize)
            .testTag("star$index")
    )
}