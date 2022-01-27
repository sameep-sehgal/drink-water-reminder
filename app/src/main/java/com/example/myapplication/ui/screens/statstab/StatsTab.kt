package com.example.myapplication.ui.screens.statstab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChart
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChartData
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.label.LabelDrawer
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.xaxis.XAxisDrawer
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.renderer.yaxis.YAxisDrawer
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChart
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartData

@Composable
fun StatsTab(
  roomDatabaseViewModel: RoomDatabaseViewModel
) {
  val onSurfaceColor = MaterialTheme.colors.onSurface
  val primaryColor = MaterialTheme.colors.primary
  Column(
    modifier = Modifier.height(200.dp)
  ) {
    Text(text = "This is Stats Tab")
    PieChart(
      pieChartData = PieChartData(
        slices = listOf(
          PieChartData.Slice(25f, Color.Red),
          PieChartData.Slice(42f, Color.Blue),
          PieChartData.Slice(23f, Color.Green)
        )
      ),
      sliceThickness = 50f
    )
  }
  Column {
    BarChart(
      barChartData = BarChartData(
        bars = listOf(
          BarChartData.Bar(100f, "1"),
          BarChartData.Bar(23f, "2"),
          BarChartData.Bar(40f, "3"),
          BarChartData.Bar(60f, "4"),
          BarChartData.Bar(80f, "5"),
          BarChartData.Bar(5f, "6"),
          BarChartData.Bar(180f, "7"),
          BarChartData.Bar(0f, "8"),
          BarChartData.Bar(150f, "9"),
          BarChartData.Bar(90f, "10"),
          BarChartData.Bar(50f, "11"),
          BarChartData.Bar(20f, "12"),
          BarChartData.Bar(10f, "13"),
          BarChartData.Bar(0f, "14"),
          BarChartData.Bar(70f, "15"),
        )
      ),
      modifier = Modifier.height(200.dp).padding(12.dp),
      yAxisDrawer = YAxisDrawer(
        axisLineColor = onSurfaceColor,
        labelTextColor = onSurfaceColor
      ),
      xAxisDrawer = XAxisDrawer(
        axisLineColor = onSurfaceColor
      ),
      labelDrawer = LabelDrawer(
        labelTextColor = onSurfaceColor,
        drawLocation = LabelDrawer.DrawLocation.XAxis
      )
    )
  }
}