package com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.xaxis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class XAxisDrawer(
  private val axisLineThickness: Dp = 1.dp,
  private val axisLineColor: Color = Color.Black
) : XAxisDrawerInterface {
  private val axisLinePaint = Paint().apply {
    isAntiAlias = true
    color = axisLineColor
    style = PaintingStyle.Stroke
  }

  override fun requiredHeight(drawScope: DrawScope): Float = with(drawScope) {
    (3f / 2f) * axisLineThickness.toPx()
  }

  override fun drawAxisLine(drawScope: DrawScope, canvas: Canvas, drawableArea: Rect) =
    with(drawScope) {
      val lineThickness = axisLineThickness.toPx()
      val y = drawableArea.top + (lineThickness / 2f)

      canvas.drawLine(
        p1 = Offset(
          x = drawableArea.left,
          y = y
        ),
        p2 = Offset(
          x = drawableArea.right,
          y = y
        ),
        paint = axisLinePaint.apply {
          strokeWidth = lineThickness
        }
      )
    }
}