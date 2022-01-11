package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.DrinkWaterReport

@Composable
fun ReportDialog(
  setShowReportDialog:(Boolean)->Unit,
  roomDatabaseViewModel: RoomDatabaseViewModel
){
  val title = "Drink Water Report"
  ShowDialog(
    title = title,
    content = { ReportDialogContent(roomDatabaseViewModel) },
    setShowDialog = setShowReportDialog,
    onConfirmButtonClick = { /*Todo*/ }
  )
}

@Composable
fun ReportDialogContent(
  roomDatabaseViewModel: RoomDatabaseViewModel
){
  val drinkLogsCount = roomDatabaseViewModel.drinkLogsCount.collectAsState()
  val waterRecordsCount = roomDatabaseViewModel.waterRecordsCount.collectAsState()
  LaunchedEffect(key1 = true) {
    roomDatabaseViewModel.getDrinkLogsCount()
    roomDatabaseViewModel.getWaterRecordsCount()
  }
  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    Text(
      text = "Streak: " +
              "${DrinkWaterReport.calculateStreak()}"
    )
    Text(
      text = "Drink Frequency: " +
              "${DrinkWaterReport.calculateDrinkFrequency(
                waterRecordsCount = waterRecordsCount.value,
                drinkLogsCount = drinkLogsCount.value
              )} drinks/day"
    )
    Text(
      text = "Weekly Average: " +
              "${DrinkWaterReport.calculateWeeklyAverage()}"
    )
    Text(
      text = "Monthly Average: " +
              "${DrinkWaterReport.calculateMonthlyAverage()}"
    )
    Text(
      text = "Average Completion: " +
              "${DrinkWaterReport.calculateAverageCompletion()}%"
    )
  }
}