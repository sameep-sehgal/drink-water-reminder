package com.sameep.watertracker.ui.screens.statstab.components.charts.barchart

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
import androidx.compose.ui.platform.LocalContext
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.BarChartUtils.axisAreas
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.BarChartUtils.barDrawableArea
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.BarChartUtils.forEachWithArea
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.bar.BarDrawer
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.bar.BarDrawerInterface
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.label.LabelDrawer
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.label.LabelDrawerInterface
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.xaxis.XAxisDrawer
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.xaxis.XAxisDrawerInterface
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.yaxis.YAxisDrawer
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.renderer.yaxis.YAxisDrawerInterface
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
  val context = LocalContext.current

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

        // Draw yAxis line
        yAxisDrawer.drawAxisLabels(
          drawScope = this,
          canvas = canvas,
          drawableArea = yAxisArea,
          barDrawableArea = barDrawableArea
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
          bar = bar,
          barArea = barArea,
          xAxisArea = xAxisArea,
          resources = context.resources
        )
      }
    }
  }
}