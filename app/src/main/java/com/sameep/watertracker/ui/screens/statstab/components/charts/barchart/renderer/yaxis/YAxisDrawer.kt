package com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.yaxis

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.utils.toLegacyInt

typealias LabelFormatter = (value: Float) -> String

class YAxisDrawer(
  private val labelTextSize: TextUnit = 12.sp,
  private val labelTextColor: Color = Color.Gray,
  private val labelValueFormatter: LabelFormatter = { value -> "${value.toInt()}%" },
  private val axisLineThickness: Dp = 1.dp,
  private val axisLineColor: Color = Color.Gray
) : YAxisDrawerInterface {
  private val axisLinePaint = Paint().apply {
    isAntiAlias = true
    color = axisLineColor
    style = PaintingStyle.Stroke
  }
  private val textPaint = android.graphics.Paint().apply {
    isAntiAlias = true
    color = labelTextColor.toLegacyInt()
  }
  private val textBounds = android.graphics.Rect()

  override fun drawAxisLine(
    drawScope: DrawScope,
    canvas: Canvas,
    drawableArea: Rect
  ) = with(drawScope) {
    val lineThickness = axisLineThickness.toPx()
    val x = drawableArea.right - (lineThickness / 2f)

    canvas.drawLine(
      p1 = Offset(
        x = x,
        y = drawableArea.top - (labelTextSize.toPx()/2f)
      ),
      p2 = Offset(
        x = x,
        y = drawableArea.bottom
      ),
      paint = axisLinePaint.apply {
        strokeWidth = lineThickness
      }
    )
  }

  override fun drawAxisLabels(
    drawScope: DrawScope,
    canvas: Canvas,
    drawableArea: Rect,
    barDrawableArea: Rect
  ) = with(drawScope) {
    val labelPaint = textPaint.apply {
      textSize = labelTextSize.toPx()
      textAlign = android.graphics.Paint.Align.RIGHT
    }
    val totalHeight = drawableArea.height
    val labelCount = 5

    val minVal = 0f
    var value:Float

    for (i in 0..labelCount) {
      value = minVal + i*20f

      val label = labelValueFormatter(value)
      val x = drawableArea.right - axisLineThickness.toPx() - (labelTextSize.toPx() / 2f)

      labelPaint.getTextBounds(label, 0, label.length, textBounds)

      val y =
        drawableArea.bottom - (i * (totalHeight / labelCount)) + (textBounds.height() / 2f)

      canvas.nativeCanvas.drawText(label, x, y, labelPaint)
      if(value > 1f){
        canvas.nativeCanvas.drawLine(
          drawableArea.right,
          y - (labelTextSize.toPx() / 2.5f),
          barDrawableArea.right,
          y - (labelTextSize.toPx() / 2.5f),
          labelPaint
        )
      }
    }
  }
}