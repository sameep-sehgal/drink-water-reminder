package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.TimeString
import java.util.*

@Composable
fun CustomAddWaterDialog(
  waterUnit: String,
  setShowCustomAddWaterDialog:(Boolean)->Unit,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  dailyWaterRecord: DailyWaterRecord,
  selectedDate:String = dailyWaterRecord.date
){
  val title = "Add Water Intake"
  var time by remember { mutableStateOf(TimeString.longToString(Calendar.getInstance().timeInMillis)) }
  var amount by remember { mutableStateOf(RecommendedWaterIntake.defaultCustomAddWaterIntake(waterUnit)) }
  var icon by remember { mutableStateOf(RecommendedWaterIntake.DEFAULT_CUSTOM_ADD_WATER_CONTAINTER) }
  val setTime = {value:String -> time = value}
  val setAmount = {value:Int -> amount = value}
  val setIcon = {value:Int -> icon = value}

  ShowDialog(
    title = title,
    content = { CustomAddWaterDialogContent(
      time = time,
      amount = amount,
      icon = icon,
      setTime = setTime,
      setAmount = setAmount,
      setIcon = setIcon,
      waterUnit = waterUnit
    ) },
    setShowDialog = setShowCustomAddWaterDialog,
    onConfirmButtonClick = {
      val calendar = Calendar.getInstance()
      calendar.set(Calendar.HOUR_OF_DAY, time.split(":")[0].toInt())
      calendar.set(Calendar.MINUTE, time.split(":")[1].toInt())
      roomDatabaseViewModel.insertDrinkLog(
        DrinkLogs(
          amount = amount,
          icon = icon,
          time = calendar.timeInMillis,
          date = selectedDate
        )
      )
      roomDatabaseViewModel.updateDailyWaterRecord(
        DailyWaterRecord(
          date = selectedDate,
          goal = dailyWaterRecord.goal,
          currWaterAmount = dailyWaterRecord.currWaterAmount + amount
        )
      )
    }
  )
}

@Composable
fun CustomAddWaterDialogContent(
  time:String,
  amount:Int,
  icon:Int,
  setTime:(String) -> Unit,
  setAmount:(Int) -> Unit,
  setIcon:(Int) -> Unit,
  waterUnit:String
){
  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    EditDrinkLogDialogContent(
      time = time,
      amount = amount,
      icon = icon,
      setTime = setTime,
      setAmount = setAmount,
      setIcon = setIcon,
      waterUnit = waterUnit
    )
  }
}