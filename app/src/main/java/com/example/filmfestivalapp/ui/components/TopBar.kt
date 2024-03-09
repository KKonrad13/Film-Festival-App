package com.example.filmfestivalapp.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import com.example.filmfestivalapp.ui.Dimensions

@Composable
fun FilmFestivalTopBar(
    leftContent: @Composable BoxScope.() -> Unit = {},
    centerContent: @Composable BoxScope.() -> Unit = {},
    rightContent: @Composable BoxScope.() -> Unit = {},
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimensions.space60)
        ) {
            Box(modifier = Modifier.width(Dimensions.space48)) {
                leftContent()
            }
            Box(modifier = Modifier.weight(1f)) {
                centerContent()
            }
            Box(modifier = Modifier.width(Dimensions.space48)) {
                rightContent()
            }
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .width(Dimensions.space1)
                .background(Color.Black)
                .alpha(.2f)
        )
    }
}