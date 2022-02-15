package com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.label

import android.content.res.Resources
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.BarChartData
import com.sameep.watertracker.utils.toLegacyInt
import android.graphics.Bitmap
import android.graphics.Paint

import android.graphics.drawable.BitmapDrawable

import android.graphics.drawable.Drawable




class LabelDrawer(
  private val drawLocation: DrawLocation = DrawLocation.Inside,
  private val labelTextSize: TextUnit = 12.sp,
  private val labelTextColor: Color = Color.Black
): LabelDrawerInterface {
  private val _labelTextArea: Float? = null
  private val paint = Paint().apply {
    this.textAlign = Paint.Align.CENTER
    this.color = labelTextColor.toLegacyInt()
  }

  override fun requiredAboveBarHeight(drawScope: DrawScope): Float = when (drawLocation) {
    DrawLocation.Outside -> (3f / 2f) * labelTextHeight(drawScope)
    DrawLocation.Inside,
    DrawLocation.XAxis -> 0f
  }

  override fun requiredXAxisHeight(drawScope: DrawScope): Float = when (drawLocation) {
    DrawLocation.XAxis -> labelTextHeight(drawScope)
    DrawLocation.Inside,
    DrawLocation.Outside -> 0f
  }

  override fun drawLabel(
    drawScope: DrawScope,
    canvas: Canvas,
    bar: BarChartData.Bar,
    barArea: Rect,
    xAxisArea: Rect,
    resources:Resources
  ) = with(drawScope) {
    val xCenter = barArea.left + (barArea.width / 2)

    val yCenter = when (drawLocation) {
      DrawLocation.Inside -> (barArea.top + barArea.bottom) / 2
      DrawLocation.Outside -> (barArea.top) - labelTextSize.toPx() / 2
      DrawLocation.XAxis -> barArea.bottom + labelTextHeight(drawScope)
    }

    if(bar.value >= 100f){
      canvas.nativeCanvas.drawCircle(
        xCenter,
        (barArea.top) - labelTextSize.toPx() / 2,
        barArea.width/2,
        Paint().apply {
          this.color = android.graphics.Color.rgb(227,227,0)
        }
      )
    }
    canvas.nativeCanvas.drawText(bar.label, xCenter, yCenter, paint(drawScope))
  }

  private fun labelTextHeight(drawScope: DrawScope) = with(drawScope) {
    _labelTextArea ?: ((3f / 2f) * labelTextSize.toPx())
  }

  private fun paint(drawScope: DrawScope) = with(drawScope) {
    paint.apply {
      this.textSize = labelTextSize.toPx()
    }
  }

  enum class DrawLocation {
    Inside,
    Outside,
    XAxis
  }

  fun drawableToBitmap(drawable: Drawable) : Bitmap{
    if (drawable is BitmapDrawable) {
      return drawable.bitmap
    }
    val bitmap =
      Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
    val canvas = android.graphics.Canvas()
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
  }
}