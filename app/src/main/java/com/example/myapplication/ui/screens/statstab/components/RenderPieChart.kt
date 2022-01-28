package com.example.myapplication.ui.screens.statstab.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChart
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartData

@Composable
fun RenderPieChart() {
  Card(
    elevation = 6.dp,
    modifier = Modifier.padding(6.dp)
  ) {
    Column(
      modifier = Modifier.padding(8.dp)
    ) {
      Text(
        text = "Intake by Beverage",
        fontSize = 16.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.size(8.dp))
      PieChart(
        pieChartData = PieChartData(
          slices = listOf(
            PieChartData.Slice(25f, Color.Red),
            PieChartData.Slice(42f, Color.Blue),
            PieChartData.Slice(23f, Color.Green)
          )
        ),
        sliceThickness = 50f,
        modifier = Modifier
          .height(180.dp)
          .padding(12.dp)
      )
    }
  }
}