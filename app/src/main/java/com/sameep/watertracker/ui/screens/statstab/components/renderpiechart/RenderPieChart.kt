package com.sameep.watertracker.ui.screens.statstab.components.renderpiechart

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.ui.screens.statstab.components.charts.piechart.PieChart
import com.sameep.watertracker.ui.screens.statstab.components.charts.piechart.PieChartData
import com.sameep.watertracker.ui.screens.statstab.components.charts.piechart.PieChartUtils

@Composable
fun RenderPieChart(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  startDate:String,
  endDate:String,
  waterUnit: String
) {
  val drinkLogsList = roomDatabaseViewModel.statsDrinkLogsList.collectAsState()
  var pieChartData by remember {
    mutableStateOf(
      mutableListOf<PieChartData.Slice>()
    )
  }
  var otherBeverageList by remember {
    mutableStateOf(
      mutableListOf<PieChartData.Slice>()
    )
  }
  var totalAmountDrunk by remember {
    mutableStateOf(
      0
    )
  }

  LaunchedEffect(startDate, endDate) {
    roomDatabaseViewModel.getStatsDrinkLogsList(startDate, endDate)
  }

  LaunchedEffect(drinkLogsList.value) {
    val beverageData = PieChartUtils.getBeverageData(drinkLogsList.value)
    pieChartData = beverageData[0]
    otherBeverageList = beverageData[1]
    totalAmountDrunk = beverageData[2][0].value.toInt()
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

      Box(
        modifier = Modifier
          .height(180.dp)
          .padding(12.dp),
        contentAlignment = Alignment.Center
      ) {
        if (pieChartData.isNullOrEmpty()) {
          Text(
            text = "You haven't drunk any beverage yet.",
            fontSize = 12.sp
          )
        } else {
          PieChart(
            pieChartData = PieChartData(
              slices = pieChartData
            ),
            sliceThickness = 45f,
          )

          PieChartTotalAmountDrunk(
            totalAmountDrunk = totalAmountDrunk,
            waterUnit = waterUnit
          )
        }
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