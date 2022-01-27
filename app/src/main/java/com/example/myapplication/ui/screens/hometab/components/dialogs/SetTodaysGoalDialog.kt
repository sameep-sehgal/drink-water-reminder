package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.myapplication.PreferenceDataStoreViewModel
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.DateString
import com.example.myapplication.utils.DrinkWaterReport

@Composable
fun SetTodaysGoalDialog(
  setShowSetTodaysGoalDialog:(Boolean)->Unit,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  waterUnit:String
){
  val title = "Drink Water Report"
  ShowDialog(
    title = title,
    content = { ReportDialogContent(roomDatabaseViewModel,preferenceDataStoreViewModel,waterUnit) },
    setShowDialog = setShowSetTodaysGoalDialog,
    onConfirmButtonClick = { /*Todo*/ }
  )
}

@Composable
fun ReportDialogContent(
  roomDatabaseViewModel: RoomDatabaseViewModel,
  preferenceDataStoreViewModel: PreferenceDataStoreViewModel,
  waterUnit: String
){
  val drinkLogsCount = roomDatabaseViewModel.drinkLogsCount.collectAsState()
  val waterRecordsCount = roomDatabaseViewModel.waterRecordsCount.collectAsState()
  val completedWaterRecordsCount = roomDatabaseViewModel.completedWaterRecordsCount.collectAsState()
  val weekWaterRecordsList = roomDatabaseViewModel.weekWaterRecordsList.collectAsState()
  val monthWaterRecordsList = roomDatabaseViewModel.monthWaterRecordsList.collectAsState()
  val firstWaterRecordDate = preferenceDataStoreViewModel.firstWaterDataDate.collectAsState(initial = DateString.NOT_SET)
  val todaysDate = DateString.getTodaysDate()
  val totalDays = DateString.calculateDaysDifference(firstWaterRecordDate.value, todaysDate)

  LaunchedEffect(key1 = true) {
    roomDatabaseViewModel.getDrinkLogsCount()
    roomDatabaseViewModel.getWaterRecordsCount()
    roomDatabaseViewModel.getWeekWaterRecordsList()
    roomDatabaseViewModel.getMonthWaterRecordsList()
    roomDatabaseViewModel.getCompletedWaterRecordsCount()
  }

  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    Text(
      text = "Drink Frequency: " +
              "${DrinkWaterReport.calculateDrinkFrequency(
                waterRecordsCount = waterRecordsCount.value,
                drinkLogsCount = drinkLogsCount.value
              )} drinks/day"
    )
    Text(
      text = "Weekly Average: " +
              "${DrinkWaterReport.calculateAverage(weekWaterRecordsList.value, "weekly")}" +
              waterUnit
    )
    Text(
      text = "Monthly Average: " +
              "${DrinkWaterReport.calculateAverage(monthWaterRecordsList.value, "monthly")}" +
              waterUnit
    )
    Text(
      text = "Goal Completed: " +
              "${completedWaterRecordsCount.value}/" +
              "$totalDays days"
    )
    Text(
      text = "Average Completion: " +
              "${DrinkWaterReport.calculateAverageCompletion(
                completedWaterRecordsCount = completedWaterRecordsCount.value,
                totalDays = totalDays
              )}%"
    )
  }
}