package io.rob.diet.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp


// Set of Material typography styles to start with
val lightThemeTypography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        letterSpacing = 2.sp
    ),
    h1 = TextStyle(
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 28.sp
    ),
    h2 = TextStyle(
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 20.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

val darkThemeTypography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color.White
    ),
    h1 = TextStyle(
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 28.sp
    ),
    h2 = TextStyle(
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        textAlign = TextAlign.Center,
        letterSpacing = 4.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)