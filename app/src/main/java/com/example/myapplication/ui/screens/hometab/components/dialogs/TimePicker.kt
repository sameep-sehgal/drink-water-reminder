package com.example.myapplication.ui.screens.hometab.components.dialogs

import android.app.TimePickerDialog
import android.content.DialogInterface
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.data.models.ReminderTimings
import java.util.*

@Composable
fun TimePicker(
  reminderTiming: ReminderTimings? = null,
  setShowTimePicker: (Boolean) -> Unit,
  reminderReminderTimingsDialogViewModel: ReminderTimingsDialogViewModel
){
  val context = LocalContext.current
  val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> // logic to properly handle
      //Change in UI
      setShowTimePicker(false)

      //Delete old Reminder -- deleted only when update existing reminders
      if(reminderTiming != null){
        deleteOldReminder(reminderTiming, reminderReminderTimingsDialogViewModel)
      }

      //Create New Reminder
      createNewReminder(reminderReminderTimingsDialogViewModel)
    }

  val onTimePickerDialogDismiss = DialogInterface.OnDismissListener {
    setShowTimePicker(false)
  }

  //Create and show time Picker
  val timePicker = TimePickerDialog(
    context,
    timePickerDialogListener,
    8,
    0,
    true
  )
  timePicker.setOnDismissListener(onTimePickerDialogDismiss)
  timePicker.show()
}

fun deleteOldReminder(
  reminderTiming: ReminderTimings,
  reminderReminderTimingsDialogViewModel: ReminderTimingsDialogViewModel
){

}

fun createNewReminder(
  reminderReminderTimingsDialogViewModel: ReminderTimingsDialogViewModel
){

}