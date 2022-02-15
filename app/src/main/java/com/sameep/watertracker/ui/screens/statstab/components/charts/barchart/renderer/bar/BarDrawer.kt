package com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.bar

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.BarChartData

class BarDrawer: BarDrawerInterface {
  private val barPaint = Paint().apply {
    this.isAntiAlias = true
  }

  override fun drawBar(
    drawScope: DrawScope,
    canvas: Canvas,
    barArea: Rect,
    bar: BarChartData.Bar
  ) {
    canvas.drawRect(barArea, barPaint.apply {
      color = bar.color
    })
  }
}