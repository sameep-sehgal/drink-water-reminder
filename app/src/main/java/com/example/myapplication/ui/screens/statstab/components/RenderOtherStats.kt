package com.example.myapplication.ui.screens.statstab.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.ui.screens.settingstab.components.SettingsRowSelectValue
import com.example.myapplication.utils.Statistics

@Composable
fun RenderOtherStats(
  drinkLogsCount: Int,
  waterRecordsCount: Int,
  goalCompletedDaysCount: Int,
  waterRecordsList: List<DailyWaterRecord>
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
        )} ml/day",
        onSettingsRowClick = {}
      )
      SettingsRowSelectValue(
        text = "Average Completion",
        value = "${Statistics.calculateAverageCompletion(
          waterRecordsList = waterRecordsList
        )}%",
        onSettingsRowClick = {}
      )
      SettingsRowSelectValue(
        text = "Drink Frequency",
        value = "${Statistics.calculateDrinkFrequency(
          waterRecordsCount = waterRecordsCount,
          drinkLogsCount = drinkLogsCount
        )} times/day",
        onSettingsRowClick = {}
      )
      SettingsRowSelectValue(
        text = "Goal Completed",
        value = "${goalCompletedDaysCount}/${waterRecordsCount} days",
        onSettingsRowClick = {}
      )
    }
  }
}