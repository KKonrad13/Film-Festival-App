package com.example.filmfestivalapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.filmfestivalapp.R

val Mulish = FontFamily(
    Font(R.font.mulish_black, FontWeight.Black),
    Font(R.font.mulish_bold, FontWeight.Bold),
    Font(R.font.mulish_extrabold, FontWeight.ExtraBold),
    Font(R.font.mulish_medium, FontWeight.Medium),
    Font(R.font.mulish_regular, FontWeight.Normal),
    Font(R.font.mulish_semibold, FontWeight.SemiBold),
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Mulish,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 24.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = Mulish,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 18.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = Mulish,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = Mulish,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = Mulish,
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
    )
)