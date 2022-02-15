package com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.label

import android.content.res.Resources
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.BarChartData

interface LabelDrawerInterface {
  fun requiredXAxisHeight(drawScope: DrawScope): Float = 0f
  fun requiredAboveBarHeight(drawScope: DrawScope): Float = 0f

  fun drawLabel(
    drawScope: DrawScope,
    canvas: Canvas,
    bar: BarChartData.Bar,
    barArea: Rect,
    xAxisArea: Rect,
    resources: Resources
  )
}