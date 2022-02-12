package com.example.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val AppColorPrimary = Color(0xFF45b3e0)
val AppColorSecondary = Color(0xFFd2f1fc)
val VeryLightGray = Color(0xFFDADEDF)
val LightGray = Color(0xFFA7AFB2)
val SettingsSubheadingLight = Color(0x33A7AFB2)
val SettingsSubheadingDark = Color(0x66A7AFB2)

val SettingsSubheadingBg =
  @Composable{
    if (isSystemInDarkTheme())
      SettingsSubheadingDark
    else SettingsSubheadingLight
}