package com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.label

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope

interface LabelDrawerInterface {
  fun requiredXAxisHeight(drawScope: DrawScope): Float = 0f
  fun requiredAboveBarHeight(drawScope: DrawScope): Float = 0f

  fun drawLabel(
    drawScope: DrawScope,
    canvas: Canvas,
    label: String,
    barArea: Rect,
    xAxisArea: Rect
  )
}