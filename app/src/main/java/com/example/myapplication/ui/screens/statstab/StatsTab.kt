package com.example.myapplication.ui.screens.statstab

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.screens.statstab.components.RenderBarChart
import com.example.myapplication.ui.screens.statstab.components.RenderOtherStats
import com.example.myapplication.ui.screens.statstab.components.RenderPieChart
import com.example.myapplication.ui.screens.statstab.components.TopStatsTabBar
import com.example.myapplication.ui.screens.statstab.components.charts.barchart.BarChartData
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChart
import com.example.myapplication.ui.screens.statstab.components.charts.piechart.PieChartData
import com.example.myapplication.ui.screens.statstab.components.selectors.TimeLineSelector
import com.example.myapplication.ui.theme.SettingsSubheadingLight
import com.example.myapplication.utils.DateString
import com.example.myapplication.utils.Statistics
import com.example.myapplication.utils.Statistics.createBarChartData
import com.example.myapplication.utils.Statistics.createWaterRecordHashMap

@Composable
fun StatsTab(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  darkTheme:Boolean
) {
  val scrollState = rememberScrollState()
  val todaysDate = DateString.getTodaysDate()
  var waterRecordsCount by remember{ mutableStateOf(0) }
  val incrementWaterRecordsCount:()->Unit = { waterRecordsCount++ }
  var goalCompletedDaysCount by remember{ mutableStateOf(0) }
  val incrementGoalCompletedDaysCount:()->Unit = { goalCompletedDaysCount++ }
  var startDate by remember {
    mutableStateOf(DateString.getWeekStartDate(todaysDate))
  }
  val setStartDate = {date:String -> startDate = date}
  var endDate by remember {
    mutableStateOf(DateString.getWeekEndDate(todaysDate))
  }
  val setEndDate = {date:String -> endDate = date}
  var selectedStatsTimeLine by remember{ mutableStateOf(Statistics.WEEKLY) }
  val setSelectedStatsTimeLine = { value:String -> selectedStatsTimeLine = value }
  var bars by remember {
    mutableStateOf(MutableList(0) { index ->
        BarChartData.Bar(0f, "$index")
      }
    )
  }

  val waterRecordsList = roomDatabaseViewModel.waterRecordsList.collectAsState()
  val drinkLogsCount = roomDatabaseViewModel.drinkLogsCount.collectAsState()
  val firstWaterRecordDate = preferenceDataStoreViewModel.firstWaterDataDate.collectAsState(initial = DateString.NOT_SET)

  LaunchedEffect(
    key1 = selectedStatsTimeLine
  ) {
    if(selectedStatsTimeLine == Statistics.WEEKLY){
      startDate = DateString.getWeekStartDate(todaysDate)
      endDate = DateString.getWeekEndDate(todaysDate)
      Log.d("TAG", "StatsTab: $startDate $endDate")
    }
    if(selectedStatsTimeLine == Statistics.MONTHLY) {
      startDate = DateString.getMonthStartDate(todaysDate)
      endDate = DateString.getMonthEndDate(todaysDate)
    }
    roomDatabaseViewModel.getWaterRecordsList(
      startDate = startDate,
      endDate = endDate
    )
  }

  LaunchedEffect(
    key1 = startDate,
    key2 = endDate
  ) {
    roomDatabaseViewModel.getWaterRecordsList(
      startDate = startDate,
      endDate = endDate
    )
    roomDatabaseViewModel.getDrinkLogsCount(
      startDate = startDate,
      endDate = endDate
    )
  }

  LaunchedEffect(
    key1 = waterRecordsList.value,
    key2 = endDate,
    key3 = startDate
  ) {
    waterRecordsCount = 0
    goalCompletedDaysCount = 0
    val dateRecordMapper = createWaterRecordHashMap(
      waterRecordsList = waterRecordsList.value,
      incrementGoalCompletedDaysCount = incrementGoalCompletedDaysCount
    )
    bars = createBarChartData(
      startDate = startDate,
      endDate = endDate,
      dateRecordMapper = dateRecordMapper,
      selectedStatsTimeLine = selectedStatsTimeLine,
      incrementDailyWaterRecordsCount = incrementWaterRecordsCount
    )
  }

  var columnModifier: Modifier = Modifier
  if(!darkTheme) columnModifier = columnModifier.background(color = SettingsSubheadingLight)

  Column(
    modifier = columnModifier
      .fillMaxWidth()
      .verticalScroll(scrollState)
  ) {
    TopStatsTabBar(setSelectedStatsTimeLine = setSelectedStatsTimeLine)

    Spacer(modifier = Modifier.size(8.dp))

    TimeLineSelector(
      selectedStatsTimeLine = selectedStatsTimeLine,
      startDate = startDate,
      endDate = endDate,
      setStartDate = setStartDate,
      setEndDate = setEndDate,
      firstWaterRecordDate = firstWaterRecordDate.value
    )

    RenderBarChart(bars = bars)

    RenderPieChart()

    RenderOtherStats(
      drinkLogsCount = drinkLogsCount.value,
      waterRecordsCount = waterRecordsCount,
      goalCompletedDaysCount = goalCompletedDaysCount,
      waterRecordsList = waterRecordsList.value
    )

  }
}