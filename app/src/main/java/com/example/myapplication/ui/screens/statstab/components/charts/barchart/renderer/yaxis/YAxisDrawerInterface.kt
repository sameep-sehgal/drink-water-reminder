package com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.yaxis

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

interface YAxisDrawerInterface {
  fun drawAxisLine(
    drawScope: DrawScope,
    canvas: Canvas,
    drawableArea: Rect
  )

  fun drawAxisLabels(
    drawScope: DrawScope,
    canvas: Canvas,
    drawableArea: Rect,
    barDrawableArea:Rect
  )
}