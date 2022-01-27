package com.example.myapplication.ui.screens.statstab.components.charts.piechart.renderer

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartData

interface SliceDrawerInterface {
  fun drawSlice(
    drawScope: DrawScope,
    canvas: Canvas,
    area: Size,
    startAngle: Float,
    sweepAngle: Float,
    slice: PieChartData.Slice
  )
}