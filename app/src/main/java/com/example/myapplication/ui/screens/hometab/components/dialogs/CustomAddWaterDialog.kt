package com.example.myapplication.ui.screens.hometab.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.Beverage
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
  selectedDate:String = dailyWaterRecord.date,
  beverage: Beverage
){
  val title = "Add Water Intake"
  var time by remember { mutableStateOf(TimeString.longToString(Calendar.getInstance().timeInMillis)) }
  var amount by remember { mutableStateOf(RecommendedWaterIntake.defaultCustomAddWaterIntake(waterUnit)) }
  val setTime = {value:String -> time = value}
  val setAmount = {value:Int -> amount = value}

  ShowDialog(
    title = title,
    content = { CustomAddWaterDialogContent(
      time = time,
      amount = amount,
      setTime = setTime,
      setAmount = setAmount,
      waterUnit = waterUnit
    ) },
    setShowDialog = setShowCustomAddWaterDialog,
    onConfirmButtonClick = {
      val calendar = Calendar.getInstance()
      calendar.set(Calendar.HOUR_OF_DAY, time.split(":")[0].toInt())
      calendar.set(Calendar.MINUTE, time.split(":")[1].toInt())
      calendar.set(Calendar.DATE, selectedDate.split("-")[2].toInt())
      calendar.set(Calendar.MONTH, selectedDate.split("-")[1].toInt() - 1)
      calendar.set(Calendar.YEAR, selectedDate.split("-")[0].toInt())
      roomDatabaseViewModel.insertDrinkLog(
        DrinkLogs(
          amount = amount,
          icon = beverage.icon,
          time = calendar.timeInMillis,
          date = selectedDate,
          beverage = beverage.name
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
  setTime:(String) -> Unit,
  setAmount:(Int) -> Unit,
  waterUnit:String
){
  Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
    EditDrinkLogDialogContent(
      time = time,
      amount = amount,
      setTime = setTime,
      setAmount = setAmount,
      waterUnit = waterUnit
    )
  }
}