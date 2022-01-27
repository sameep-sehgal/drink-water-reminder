package com.example.myapplication.ui.screens.statstab.components.charts.barchart

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChartUtils.axisAreas
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChartUtils.barDrawableArea
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChartUtils.forEachWithArea
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.bar.BarDrawer
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.bar.BarDrawerInterface
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.label.LabelDrawer
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.label.LabelDrawerInterface
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.xaxis.XAxisDrawer
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.xaxis.XAxisDrawerInterface
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.yaxis.YAxisDrawer
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.yaxis.YAxisDrawerInterface
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.chartAnimation

@Composable
fun BarChart(
  barChartData: BarChartData,
  modifier: Modifier = Modifier,
  animation: AnimationSpec<Float> = chartAnimation(),
  barDrawer: BarDrawerInterface = BarDrawer(),
  xAxisDrawer: XAxisDrawerInterface = XAxisDrawer(),
  yAxisDrawer: YAxisDrawerInterface = YAxisDrawer(),
  labelDrawer: LabelDrawerInterface = LabelDrawer()
) {
  val transitionAnimation = remember(barChartData.bars) { Animatable(initialValue = 0f) }

  LaunchedEffect(barChartData.bars) {
    transitionAnimation.animateTo(1f, animationSpec = animation)
  }

  val progress = transitionAnimation.value

  Canvas(modifier = modifier
    .fillMaxSize()
    .drawBehind {
      drawIntoCanvas { canvas ->
        val (xAxisArea, yAxisArea) = axisAreas(
          drawScope = this,
          totalSize = size,
          xAxisDrawer = xAxisDrawer,
          labelDrawer = labelDrawer
        )
        val barDrawableArea = barDrawableArea(xAxisArea)

        // Draw yAxis line.
        yAxisDrawer.drawAxisLine(
          drawScope = this,
          canvas = canvas,
          drawableArea = yAxisArea
        )

        // Draw xAxis line.
        xAxisDrawer.drawAxisLine(
          drawScope = this,
          canvas = canvas,
          drawableArea = xAxisArea
        )
        // Draw each bar.
        barChartData.forEachWithArea(
          this,
          barDrawableArea,
          progress,
          labelDrawer
        ) { barArea, bar ->
          barDrawer.drawBar(
            drawScope = this,
            canvas = canvas,
            barArea = barArea,
            bar = bar
          )
        }
      }
    }
  ) {
    /**
     *  Typically we could draw everything here, but because of the lack of canvas.drawText
     *  APIs we have to use Android's `nativeCanvas` which seems to be drawn behind
     *  Compose's canvas.
     */
    drawIntoCanvas { canvas ->
      val (xAxisArea, yAxisArea) = axisAreas(
        drawScope = this,
        totalSize = size,
        xAxisDrawer = xAxisDrawer,
        labelDrawer = labelDrawer
      )
      val barDrawableArea = barDrawableArea(xAxisArea)

      barChartData.forEachWithArea(
        this,
        barDrawableArea,
        progress,
        labelDrawer
      ) { barArea, bar ->
        labelDrawer.drawLabel(
          drawScope = this,
          canvas = canvas,
          label = bar.label,
          barArea = barArea,
          xAxisArea = xAxisArea
        )
      }

      yAxisDrawer.drawAxisLabels(
        drawScope = this,
        canvas = canvas,
        minValue = barChartData.minYValue,
        maxValue = barChartData.maxYValue,
        drawableArea = yAxisArea
      )
    }
  }
}