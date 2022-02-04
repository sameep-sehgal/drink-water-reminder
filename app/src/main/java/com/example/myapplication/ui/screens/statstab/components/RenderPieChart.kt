package com.example.myapplication.ui.screens.statstab.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChart
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartData
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartUtils

@Composable
fun RenderPieChart(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  startDate:String,
  endDate:String,
) {
  val drinkLogsList = roomDatabaseViewModel.statsDrinkLogsList.collectAsState()
  var pieChartData by remember{ mutableStateOf(
    mutableListOf<PieChartData.Slice>()
  ) }

  LaunchedEffect(startDate, endDate) {
    roomDatabaseViewModel.getStatsDrinkLogsList(startDate, endDate)
  }

  LaunchedEffect(drinkLogsList.value) {
    val data = HashMap<String, PieChartData.Slice>()
    for(drinkLog in drinkLogsList.value) {
      val curr = data[drinkLog.beverage]
      if(curr != null){
        curr.value += drinkLog.amount
      }else{
        data[drinkLog.beverage] =
          PieChartData.Slice(
            value = drinkLog.amount.toFloat(),
            color = Color.Red,
            name = drinkLog.beverage
          )
      }
    }
    pieChartData = mutableListOf()
    for((_, value) in data) {
      pieChartData.add(value)
    }
    pieChartData.sortByDescending { it.value }
    pieChartData.forEachIndexed { index, value ->
      value.color = PieChartUtils.getColor(index)
      Log.d("TAG", "RenderPieChart: $value")
    }
  }

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
          slices = pieChartData
        ),
        sliceThickness = 50f,
        modifier = Modifier
          .height(180.dp)
          .padding(12.dp)
      )
      Spacer(modifier = Modifier.size(8.dp))
      PieChartBeverageList(pieChartData = pieChartData)
    }
  }
}