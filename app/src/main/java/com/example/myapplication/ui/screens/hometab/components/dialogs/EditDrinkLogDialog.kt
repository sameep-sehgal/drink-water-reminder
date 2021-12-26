package com.example.myapplication.ui.screens.hometab.components.dialogs

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.widget.NumberPicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.myapplication.RoomDatabaseViewModel
import com.example.myapplication.data.models.DrinkLogs
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.utils.TimeString
import com.example.myapplication.R
import com.example.myapplication.data.models.DailyWaterRecord
import com.example.myapplication.ui.components.DropdownSelect
import com.example.myapplication.utils.RecommendedWaterIntake
import com.example.myapplication.utils.Units
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
  var icon by remember { mutableStateOf(drinkLog.icon)}
  val setTime = {value:String -> time = value}
  val setAmount = {value:Int -> amount = value}
  val setIcon = {value:Int -> icon = value}

  val title = "Edit Record"
  ShowDialog(
    title = title,
    content = { EditDrinkLogDialogContent(
      time,
      amount,
      setTime,
      setAmount,
      waterUnit
    )},
    setShowDialog = setShowEditDrinkLogDialog,
    onConfirmButtonClick = {
      calendar.set(Calendar.HOUR_OF_DAY, time.split(':')[0].toInt())
      calendar.set(Calendar.MINUTE, time.split(':')[1].toInt())
      roomDatabaseViewModel.updateDrinkLog(
        DrinkLogs(
          id = drinkLog.id,
          time = calendar.timeInMillis,
          amount = amount,
          icon = icon,
          date = drinkLog.date
        )
      )
      dailyWaterRecord.currWaterAmount -= (drinkLog.amount - amount)
      roomDatabaseViewModel.updateDailyWaterRecord(dailyWaterRecord)
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
    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
      setTime(TimeString.HHMMIntToString(hourOfDay, minute))
      //Change in UI
      setShowTimePicker(false)
    }

  val timePickerDialogDismissListener = DialogInterface.OnDismissListener {
    setShowTimePicker(false)
  }

  val numberPickerChangeListener = NumberPicker.OnValueChangeListener { picker, oldVal, newVal ->
    if(waterUnit == Units.ML){
      setAmount(newVal*10)
    }else {
      setAmount(newVal)
    }
  }

  var containerSelectorExpanded by remember { mutableStateOf(false) }
  var selectedOptionText by remember { mutableStateOf("options[0]") }

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
      Column(
        modifier = Modifier.weight(1f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Icon(
          painter = painterResource(id = R.drawable.cup_1),
          contentDescription = "container",
          modifier = Modifier.size(64.dp).padding(0.dp,8.dp)
        )
        DropdownSelect()
      }
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.weight(1f)
      ) {
        AndroidView(
          factory = { context ->
            val numberPicker = NumberPicker(context)
            numberPicker.maxValue = if(waterUnit == Units.ML) RecommendedWaterIntake.MAX_WATER_LEVEL_IN_ML_FOR_LOG/10 else RecommendedWaterIntake.MAX_WATER_LEVEL_IN_OZ_FOR_LOG
            numberPicker.minValue = if(waterUnit == Units.ML) RecommendedWaterIntake.MIN_WATER_LEVEL_IN_ML_FOR_LOG/10 else RecommendedWaterIntake.MIN_WATER_LEVEL_IN_OZ_FOR_LOG
            numberPicker.displayedValues = RecommendedWaterIntake.VALUES_FOR_WATER_LOG_NUMBER_PICKER(waterUnit).toTypedArray()
            numberPicker.setOnValueChangedListener(numberPickerChangeListener)
            numberPicker
          },
          update = {
            it.value = amount/10
          }
        )
        Text(
          text = waterUnit,
          modifier = Modifier.padding(8.dp),
          color = MaterialTheme.colors.onSurface
        )
      }
    }
//    IconSelector() TODO
  }
}