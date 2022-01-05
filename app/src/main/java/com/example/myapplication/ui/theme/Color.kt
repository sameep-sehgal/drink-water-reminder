package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val PersianGreen = Color(0xFF00a49a)
val WaterBlueDark = Color(0xFF0079ab)
val White = Color(0xFFFFFFFF)
val VeryLightGray = Color(0xFFDADEDF)
val LightGray = Color(0xFFA7AFB2)
val SelectedItemColorLight = Color(0xFFe6f3ff)
val SelectedItemColorDark = Color(0x33e6f3ff)

val SelectedItemColor =
  @Composable{
    if (isSystemInDarkTheme())
      SelectedItemColorDark
    else SelectedItemColorLight
  }

val SettingsSubheadingBg = SelectedItemColor