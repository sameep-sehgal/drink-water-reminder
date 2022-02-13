package com.example.myapplication.ui.screens.statstab.components.renderbarchart

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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

  Card(
    elevation = 6.dp,
    modifier = Modifier.padding(6.dp)
  ) {
    Column(
      modifier = Modifier.padding(8.dp)
    ) {
      Text(
        text = "Total Intake",
        fontSize = 16.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.size(16.dp))
      BarChart(
        barChartData = BarChartData(
          bars = bars
        ),
        modifier = Modifier
          .height(180.dp)
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
      Spacer(modifier = Modifier.size(8.dp))
    }
  }
}