package com.example.myapplication.ui.screens.hometab.components.dialogs

import android.app.TimePickerDialog
import android.content.DialogInterface
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun TimePicker(
  timePickerDialogListener: TimePickerDialog.OnTimeSetListener,
  onTimePickerDialogDismiss: DialogInterface.OnDismissListener,
  defaultTime:String
){
  val context = LocalContext.current

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
