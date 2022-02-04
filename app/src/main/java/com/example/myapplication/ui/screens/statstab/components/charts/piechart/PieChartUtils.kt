package com.example.myapplication.ui.screens.statstab.components.charts.piechart

import androidx.compose.ui.graphics.Color

object PieChartUtils {
  fun calculateAngle(
    sliceLength: Float,
    totalLength: Float,
    progress: Float
  ): Float {
    return 360.0f * (sliceLength * progress) / totalLength
  }

  val COLORS = listOf<Color>(
    Color(0xFF45b3e0),
    Color(0xFF00bcd8),
    Color(0xFF00c3c0),
    Color(0xFF00c79b),
    Color(0xFF55c86e),
    Color(0xFF91c33e),
    Color(0xFFc9b906),
    Color(0xFFffa600),
  )

  fun getColor(index:Int): Color {
    if(index > COLORS.size) return Color.Cyan
    return COLORS[index]
  }
}