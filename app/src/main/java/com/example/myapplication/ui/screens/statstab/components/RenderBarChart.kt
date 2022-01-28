package com.example.myapplication.ui.screens.statstab.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChart
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChartData
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.label.LabelDrawer
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.xaxis.XAxisDrawer
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.yaxis.YAxisDrawer

@Composable
fun RenderBarChart(
  bars: MutableList<BarChartData.Bar>
) {
  val onSurfaceColor = MaterialTheme.colors.onSurface

  BarChart(
    barChartData = BarChartData(
      bars = bars
    ),
    modifier = Modifier
      .height(200.dp)
      .padding(12.dp),
    yAxisDrawer = YAxisDrawer(
      axisLineColor = onSurfaceColor,
      labelTextColor = onSurfaceColor,
      labelTextSize = 11.sp
    ),
    xAxisDrawer = XAxisDrawer(
      axisLineColor = onSurfaceColor
    ),
    labelDrawer = LabelDrawer(
      labelTextColor = onSurfaceColor,
      drawLocation = LabelDrawer.DrawLocation.XAxis,
      labelTextSize = 9.5.sp
    )
  )
}