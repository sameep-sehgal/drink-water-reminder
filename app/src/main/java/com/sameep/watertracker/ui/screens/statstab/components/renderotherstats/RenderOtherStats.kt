package com.sameep.watertracker.ui.screens.statstab.components.renderotherstats

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sameep.watertracker.data.models.DailyWaterRecord
import com.sameep.watertracker.ui.screens.settingstab.components.SettingsRowSelectValue
import com.sameep.watertracker.utils.Statistics

@Composable
fun RenderOtherStats(
  drinkLogsCount: Int,
  waterRecordsCount: Int,
  goalCompletedDaysCount: Int,
  waterRecordsList: List<DailyWaterRecord>,
  waterUnit: String
) {
  Card(
    elevation = 6.dp,
    modifier = Modifier.padding(6.dp)
  ) {
    Column(
      modifier = Modifier.padding(8.dp)
    ) {
      SettingsRowSelectValue(
        text = "Average Consumption",
        value = "${Statistics.calculateAverage(
          waterRecordsList = waterRecordsList,
          daysCount = waterRecordsCount
        )} $waterUnit/day",
        onSettingsRowClick = {},
        valueFontSize = 14.sp
      )
      SettingsRowSelectValue(
        text = "Average Completion",
        value = "${Statistics.calculateAverageCompletion(
          waterRecordsList = waterRecordsList
        )}%",
        onSettingsRowClick = {},
        valueFontSize = 14.sp
      )
      SettingsRowSelectValue(
        text = "Drink Frequency",
        value = "${Statistics.calculateDrinkFrequency(
          waterRecordsCount = waterRecordsCount,
          drinkLogsCount = drinkLogsCount
        )} times/day",
        onSettingsRowClick = {},
        valueFontSize = 14.sp
      )
      SettingsRowSelectValue(
        text = "Goal Completed",
        value = "${goalCompletedDaysCount}/${waterRecordsCount} days",
        onSettingsRowClick = {},
        valueFontSize = 14.sp
      )
    }
  }
}