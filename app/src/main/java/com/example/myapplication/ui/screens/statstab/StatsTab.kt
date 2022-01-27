package com.example.myapplication.ui.screens.statstab

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChart
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChartData
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChart
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartData

@Composable
fun StatsTab(
  roomDatabaseViewModel: RoomDatabaseViewModel
) {
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
  Column() {
    BarChart(
      barChartData = BarChartData(
        bars = listOf(
          BarChartData.Bar(25f, Color.Red, "Red"),
          BarChartData.Bar(42f, Color.Blue, "Blue"),
          BarChartData.Bar(23f, Color.Green, "Green")
        )
      ),
      modifier = Modifier.height(200.dp)
    )
  }
}