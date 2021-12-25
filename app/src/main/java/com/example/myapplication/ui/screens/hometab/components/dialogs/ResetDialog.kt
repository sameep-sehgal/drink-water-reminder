package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.ui.components.ShowDialog

@Composable
fun ResetDialog(
  setShowResetDialog:(Boolean)->Unit,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  drinkLogs: List<DrinkLogs>,
  dailyWaterRecord:DailyWaterRecord
){
  val title = "Reset Today's Record?"
  ShowDialog(
    title = title,
    content = { ResetDialogContent() },
    setShowDialog = setShowResetDialog,
    onConfirmButtonClick = {
      for (log in drinkLogs){
        roomDatabaseViewModel.deleteDrinkLog(
          drinkLog = log,
          dailyWaterRecord = DailyWaterRecord(
            goal = dailyWaterRecord.goal,
            currWaterAmount = 0
          )
        )
      }
      roomDatabaseViewModel.updateDailyWaterRecord(
        DailyWaterRecord(
          goal = dailyWaterRecord.goal,
          currWaterAmount = 0
        )
      )
    }
  )
}

@Composable
fun ResetDialogContent(){
  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    Text(text = "This action cannot be undone and you will not be able to return back to the current state.")
  }
}