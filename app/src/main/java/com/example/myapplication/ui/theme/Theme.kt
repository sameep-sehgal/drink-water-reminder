package com.example.myapplication.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable



@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    primary = PersianGreen,
    primaryVariant = darkColors().background,
    secondary = PersianGreen,
    onPrimary = White
)

private val LightColorPalette = lightColors(
    primary = PersianGreen,
    primaryVariant = PersianGreen,
    secondary = PersianGreen,

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun ApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}