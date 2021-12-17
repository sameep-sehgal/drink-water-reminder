//package com.example.myapplication.ui.screens.hometab.components.dialogs
//
//import android.app.TimePickerDialog
//import android.content.DialogInterface
//import android.util.Log
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//import com.example.myapplication.data.models.ReminderTimings
//import com.example.myapplication.utils.TimeString
//
//@Composable
//fun TimePicker(
//  reminderTiming: ReminderTimings?,
//  setShowTimePicker: (Boolean) -> Unit,
//  reminderReminderTimingsDialogViewModel: ReminderTimingsDialogViewModel
//){
//  val TAG = "TimePicker"
//  Log.d(TAG, "Current selected Reminder Timing ${reminderTiming?.time} $reminderTiming")
//  val context = LocalContext.current
//  val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
//    TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> // logic to properly handle
//      Log.d(TAG, "Reminder Time Set")
//
//      var active = true
//
//      //Delete old Reminder -- deleted only when update existing reminders
//      if(reminderTiming != null){
//        if(!reminderTiming.active) active = false
//        deleteOldReminder(reminderTiming, reminderReminderTimingsDialogViewModel)
//      }
//
//      //Create New Reminder
//      createNewReminder(active, hourOfDay, minute, reminderReminderTimingsDialogViewModel)
//
//      //Change in UI
//      setShowTimePicker(false)
//    }
//
//  val onTimePickerDialogDismiss = DialogInterface.OnDismissListener {
//    setShowTimePicker(false)
//    Log.d(TAG, "Time Picker Dismissed")
//  }
//
//  //Create and show time Picker
//  val timePicker = TimePickerDialog(
//    context,
//    timePickerDialogListener,
//    8,
//    0,
//    true
//  )
//  timePicker.setOnDismissListener(onTimePickerDialogDismiss)
//  timePicker.show()
//}
//
//fun deleteOldReminder(
//  reminderTiming: ReminderTimings,
//  reminderReminderTimingsDialogViewModel: ReminderTimingsDialogViewModel
//){
//  reminderReminderTimingsDialogViewModel
//    .deleteReminderTiming(reminderTiming = reminderTiming)
//
//  //TODO("Delete existing Repeating Notification for this time")
//}
//
//fun createNewReminder(
//  active:Boolean,
//  hourOfDay:Int,
//  minute:Int,
//  reminderReminderTimingsDialogViewModel: ReminderTimingsDialogViewModel
//){
//  Log.d("", "Create New Reminder $hourOfDay:$minute $active")
//  reminderReminderTimingsDialogViewModel
//    .insertReminderTiming(
//      ReminderTimings(
//        time = TimeString.HHMMIntToString(hourOfDay = hourOfDay, minute = minute),
//        active = active
//      )
//    )
//
//  //TODO("Create new Repeating Notification for this time")
//}