package com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.xaxis

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

interface XAxisDrawerInterface {
  fun requiredHeight(drawScope: DrawScope): Float

  fun drawAxisLine(
    drawScope: DrawScope,
    canvas: Canvas,
    drawableArea: Rect
  )
}