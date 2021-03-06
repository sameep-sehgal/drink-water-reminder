package com.sameep.watertracker.ui.screens.statstab

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FabPosition
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.sameep.watertracker.EditHistoryActivity
import com.sameep.watertracker.PreferenceDataStoreViewModel
import com.sameep.watertracker.RoomDatabaseViewModel
import com.sameep.watertracker.ui.screens.statstab.components.buttons.EditHistoryButton
import com.sameep.watertracker.ui.screens.statstab.components.charts.barchart.BarChartData
import com.sameep.watertracker.ui.screens.statstab.components.renderbarchart.RenderBarChart
import com.sameep.watertracker.ui.screens.statstab.components.renderotherstats.RenderOtherStats
import com.sameep.watertracker.ui.screens.statstab.components.renderpiechart.RenderPieChart
import com.sameep.watertracker.ui.screens.statstab.components.topstatstabbar.selectors.TimeLineSelector
import com.sameep.watertracker.ui.screens.statstab.components.topstatstabbar.TopStatsTabBar
import com.sameep.watertracker.ui.theme.SettingsSubheadingLight
import com.sameep.watertracker.utils.DateString
import com.sameep.watertracker.utils.Statistics
import com.sameep.watertracker.utils.Statistics.createBarChartData
import com.sameep.watertracker.utils.Statistics.createWaterRecordHashMap
import com.sameep.watertracker.utils.Units

@Composable
fun StatsTab(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  darkTheme:Boolean
) {
  val scrollState = rememberScrollState()
  val todaysDate = DateString.getTodaysDate()
  val context = LocalContext.current
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
  val waterUnit = preferenceDataStoreViewModel.waterUnit.collectAsState(initial = Units.ML)

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
      incrementDailyWaterRecordsCount = incrementWaterRecordsCount,
      todaysDate = todaysDate,
      firstWaterDataDate = firstWaterRecordDate.value
    )
  }

  var columnModifier:Modifier = Modifier
    .fillMaxWidth()
    .verticalScroll(scrollState)
  if(!darkTheme) columnModifier = columnModifier.background(color = SettingsSubheadingLight)

  Scaffold(
    floatingActionButton = {
      Column(horizontalAlignment = Alignment.CenterHorizontally){
        EditHistoryButton(onClick = {
          val i = Intent(context, EditHistoryActivity::class.java)
          context.startActivity(i)
        })
      }
    },
    floatingActionButtonPosition = FabPosition.End,
    isFloatingActionButtonDocked = true
  ) {
    Column(
      modifier = columnModifier
    ) {
      TopStatsTabBar(setSelectedStatsTimeLine = setSelectedStatsTimeLine)

      Spacer(modifier = Modifier.size(8.dp))

      TimeLineSelector(
        selectedStatsTimeLine = selectedStatsTimeLine,
        startDate = startDate,
        endDate = endDate,
        setStartDate = setStartDate,
        setEndDate = setEndDate,
        firstWaterRecordDate = firstWaterRecordDate.value,
        context = context
      )

      RenderBarChart(bars = bars)

      RenderPieChart(
        roomDatabaseViewModel = roomDatabaseViewModel,
        startDate = startDate,
        endDate = endDate,
        waterUnit = waterUnit.value
      )

      RenderOtherStats(
        drinkLogsCount = drinkLogsCount.value,
        waterRecordsCount = waterRecordsCount,
        goalCompletedDaysCount = goalCompletedDaysCount,
        waterRecordsList = waterRecordsList.value,
        waterUnit = waterUnit.value
      )

      Spacer(modifier = Modifier.size(72.dp))
    }
  }
}