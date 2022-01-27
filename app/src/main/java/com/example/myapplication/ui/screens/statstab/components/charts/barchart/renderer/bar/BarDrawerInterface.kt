package com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.bar

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChartData

interface BarDrawerInterface {
  fun drawBar(
    drawScope: DrawScope,
    canvas: Canvas,
    barArea: Rect,
    bar: BarChartData.Bar
  )
}