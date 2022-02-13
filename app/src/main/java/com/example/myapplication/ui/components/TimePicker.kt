package com.example.myapplication.ui.components

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.utils.TimeString

@Composable
fun TimePicker(
  defaultTime:String,//Format HH:MM
  setShowTimePicker: (Boolean) -> Unit,
  setSelectedReminderPeriod: (String) -> Unit
){
  val TAG = "TimePicker"
  val context = LocalContext.current
  val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> // logic to properly handle
      Log.d(TAG, "Reminder Time Set")

      val res:String = TimeString.HHMMIntToString(hourOfDay, minute)
      setSelectedReminderPeriod(res)

      //Change in UI
      setShowTimePicker(false)
    }

  val onTimePickerDialogDismiss = DialogInterface.OnDismissListener {
    setShowTimePicker(false)
    Log.d(TAG, "Time Picker Dismissed")
  }

  //Create and show time Picker
  val timePicker = TimePickerDialog(
    context,
    timePickerDialogListener,
    defaultTime.split(':')[0].toInt(),
    defaultTime.split(':')[1].toInt(),
    true
  )
  timePicker.setOnDismissListener(onTimePickerDialogDismiss)
  timePicker.show()
}
