package com.example.myapplication.ui.screens.statstab

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.screens.statstab.components.RenderBarChart
import com.example.myapplication.ui.screens.statstab.components.TopStatsTabBar
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChartData
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChart
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartData
import com.example.myapplication.utils.DateString
import com.example.myapplication.utils.Statistics
import com.example.myapplication.utils.Statistics.createBarChartData
import com.example.myapplication.utils.Statistics.createWaterRecordHashMap

@Composable
fun StatsTab(
  roomDatabaseViewModel: RoomDatabaseViewModel
) {
  val waterRecordsList = roomDatabaseViewModel.waterRecordsList.collectAsState()
  val todaysDate = DateString.getTodaysDate()
  var startDate by remember {
    mutableStateOf(DateString.getWeekStartDate(todaysDate))
  }
  var endDate by remember {
    mutableStateOf(DateString.getWeekEndDate(todaysDate))
  }
  var selectedStatsTimeLine by remember{ mutableStateOf(Statistics.WEEKLY) }
  val setSelectedStatsTimeLine = { value:String -> selectedStatsTimeLine = value }

  var bars by remember {
    mutableStateOf(MutableList(0) { index ->
        BarChartData.Bar(0f, "$index")
      }
    )
  }

  LaunchedEffect(
    key1 = selectedStatsTimeLine
  ) {
    if(selectedStatsTimeLine == Statistics.WEEKLY){
      startDate = DateString.getWeekStartDate(todaysDate)
      endDate = DateString.getWeekEndDate(todaysDate)
      Log.d("TAG", "StatsTab: $startDate $endDate")
    }
    if(selectedStatsTimeLine == Statistics.MONTHLY) {
      startDate = "2022-01-01"
      endDate = "2022-01-31"
    }
    if(selectedStatsTimeLine == Statistics.YEARLY){
      
    }
    roomDatabaseViewModel.getWaterRecordsList(
      startDate = startDate,
      endDate = endDate
    )
  }

  LaunchedEffect(
    key1 = waterRecordsList.value,
    key2 = endDate,
    key3 = startDate
  ) {
    val dateRecordMapper = createWaterRecordHashMap(waterRecordsList = waterRecordsList.value)
    bars = createBarChartData(
      startDate = startDate,
      endDate = endDate,
      dateRecordMapper = dateRecordMapper,
      selectedStatsTimeLine = selectedStatsTimeLine
    )
  }

  TopStatsTabBar(setSelectedStatsTimeLine = setSelectedStatsTimeLine)

  Spacer(modifier = Modifier.size(16.dp))

  RenderBarChart(bars = bars)

  Spacer(modifier = Modifier.size(16.dp))

  Column(
    modifier = Modifier.height(200.dp)
  ) {
    Text(text = "This is Stats Tab $selectedStatsTimeLine")
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
}