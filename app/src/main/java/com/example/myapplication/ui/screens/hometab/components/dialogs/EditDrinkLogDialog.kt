package com.example.myapplication.ui.screens.hometab.components.dialogs

import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.TimeString
import com.example.myapplication.R
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.ui.components.WaterQuantityPicker
import java.util.*

@Composable
fun EditDrinkLogDialog(
  drinkLog:DrinkLogs,
  setShowEditDrinkLogDialog:(Boolean)->Unit,
  roomDatabaseViewModel: RoomDatabaseViewModel,
  waterUnit: String,
  dailyWaterRecord: DailyWaterRecord
){
  val calendar = Calendar.getInstance()
  calendar.time = Date(drinkLog.time)
  var time by remember { mutableStateOf(TimeString.longToString(drinkLog.time)) }
  var amount by remember { mutableStateOf(drinkLog.amount)}
  val setTime = {value:String -> time = value}
  val setAmount = {value:Int -> amount = value}

  val title = "Edit Record"
  ShowDialog(
    title = title,
    content = { EditDrinkLogDialogContent(
      time = time,
      amount = amount,
      setTime = setTime,
      setAmount = setAmount,
      waterUnit = waterUnit
    )},
    setShowDialog = setShowEditDrinkLogDialog,
    onConfirmButtonClick = {
      calendar.set(Calendar.HOUR_OF_DAY, time.split(':')[0].toInt())
      calendar.set(Calendar.MINUTE, time.split(':')[1].toInt())
      val selectedDate = dailyWaterRecord.date
      calendar.set(Calendar.DATE, selectedDate.split("-")[2].toInt())
      calendar.set(Calendar.MONTH, selectedDate.split("-")[1].toInt() - 1)
      calendar.set(Calendar.YEAR, selectedDate.split("-")[0].toInt())
      roomDatabaseViewModel.updateDrinkLog(
        DrinkLogs(
          id = drinkLog.id,
          time = calendar.timeInMillis,
          amount = amount,
          icon = drinkLog.icon,
          date = drinkLog.date,
          beverage = drinkLog.beverage
        )
      )
      dailyWaterRecord.currWaterAmount -= (drinkLog.amount - amount)
      roomDatabaseViewModel.updateDailyWaterRecord(
        DailyWaterRecord(
          date = dailyWaterRecord.date,
          goal = dailyWaterRecord.goal,
          currWaterAmount = dailyWaterRecord.currWaterAmount
        )
      )
    }
  )
}

@Composable
fun EditDrinkLogDialogContent(
  time:String,
  amount:Int,
  setTime:(String) -> Unit,
  setAmount:(Int) -> Unit,
  waterUnit:String
){
  val (showTimePicker, setShowTimePicker) =  remember { mutableStateOf(false) }
  val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
    TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
      setTime(TimeString.HHMMIntToString(hourOfDay, minute))
      //Change in UI
      setShowTimePicker(false)
    }

  val timePickerDialogDismissListener = DialogInterface.OnDismissListener {
    setShowTimePicker(false)
  }


  Column(
    modifier = Modifier.background(MaterialTheme.colors.background),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    if(showTimePicker){
      TimePicker(
        timePickerDialogListener = timePickerDialogListener,
        onTimePickerDialogDismiss = timePickerDialogDismissListener,
        defaultTime = time
      )
    }
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(
        modifier = Modifier
          .clickable {
            setShowTimePicker(true)
          }
          .padding(4.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          painter = painterResource(id = R.drawable.clock_icon),
          contentDescription = "Clock Icon",
          modifier = Modifier
            .size(22.dp)
            .padding(2.dp, 0.dp),
          tint = MaterialTheme.colors.onSurface
        )
        Text(
          text = time,
          fontSize = 20.sp,
          modifier = Modifier.padding(2.dp,0.dp),
          color = MaterialTheme.colors.onSurface
        )
      }
    }
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.Center
    ) {
      WaterQuantityPicker(
        waterUnit = waterUnit,
        amount = amount,
        setAmount = setAmount
      )
      Text(
        text = waterUnit,
        modifier = Modifier.padding(8.dp),
        color = MaterialTheme.colors.onSurface
      )
    }
  }
}