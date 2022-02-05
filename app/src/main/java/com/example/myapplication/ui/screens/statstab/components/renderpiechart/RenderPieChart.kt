package com.example.myapplication.ui.screens.statstab.components.renderpiechart

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
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
  waterUnit: String
) {
  val drinkLogsList = roomDatabaseViewModel.statsDrinkLogsList.collectAsState()
  var pieChartData by remember{ mutableStateOf(
    mutableListOf<PieChartData.Slice>()
  ) }
  var otherBeverageList by remember{ mutableStateOf(
    mutableListOf<PieChartData.Slice>()
  ) }

  LaunchedEffect(startDate, endDate) {
    roomDatabaseViewModel.getStatsDrinkLogsList(startDate, endDate)
  }

  LaunchedEffect(drinkLogsList.value) {
    val beverageData = PieChartUtils.getBeverageData(drinkLogsList.value)
    pieChartData = beverageData[0]
    otherBeverageList = beverageData[1]
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
      if(pieChartData.isNullOrEmpty()){
        Text(
          text = "You haven't drunk any beverage yet.",
          fontSize = 14.sp
        )
      }else {
        PieChart(
          pieChartData = PieChartData(
            slices = pieChartData
          ),
          sliceThickness = 50f,
          modifier = Modifier
            .height(180.dp)
            .padding(12.dp)
        )
      }
      Spacer(modifier = Modifier.size(8.dp))
      PieChartBeverageList(
        pieChartData = pieChartData,
        waterUnit = waterUnit,
        otherBeverageList = otherBeverageList
      )
    }
  }
}