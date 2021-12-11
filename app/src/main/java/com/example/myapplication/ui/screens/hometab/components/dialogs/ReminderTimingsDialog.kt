package com.example.myapplication.ui.screens.hometab.components.dialogs

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.data.models.ReminderTimings
import com.example.myapplication.ui.components.ShowDialog
import com.example.myapplication.ui.theme.Typography

@Composable
fun ReminderTimingsDialog(setShowReminderTimingsDialog: (Boolean)->Unit){
  val title = "Reminder Timings"
  ShowDialog(title = title,content = { ReminderTimingsDialogContent() }, setShowDialog = setShowReminderTimingsDialog)
}

@Composable
fun ReminderTimingsDialogContent(){
  val reminderTimings: List<ReminderTimings> = emptyList()
  val (showTimePicker, setShowTimePicker) =  remember { mutableStateOf(false) }
  val reminderTimingsDialogViewModel = viewModel<ReminderTimingsDialogViewModel>()
  val selectedReminderTiming:MutableState<ReminderTimings?> =  remember { mutableStateOf(null) }

  if(showTimePicker){
    Log.d("$showTimePicker", "ReminderTimingsDialogContent: Show Time Picker")
    TimePicker(
      reminderTiming = selectedReminderTiming.component1(),
      setShowTimePicker = setShowTimePicker,
      reminderReminderTimingsDialogViewModel = reminderTimingsDialogViewModel
    )
  }

  Column(
      modifier = Modifier.background(MaterialTheme.colors.background)
  ) {
    reminderTimings.forEach {
      Row {
       Text(modifier = Modifier.weight(1f)
           .clickable {
             selectedReminderTiming.value = it
           },
         text = it.time,
         style = Typography.body1
       )
       Switch(
         checked = it.active,
         onCheckedChange = { TODO() }
       )
        IconButton(
          onClick = { /*TODO*/ }
        ) {
          Icon(
            painter = painterResource(id = R.drawable.delete_icon),
            contentDescription = "Delete Reminder"
          )
        }
      }
    }
  }
  Row(
    horizontalArrangement = Arrangement.Center
  ) {
     FloatingActionButton(
       onClick = {
         selectedReminderTiming.value = null
         Log.d("Add Reminder", "Reminder On Click")
         setShowTimePicker(true)
       }
     ) {
         Icon(
           painter = painterResource(id = R.drawable.add_icon),
           contentDescription = "Add new Reminder"
         )
     }
  }
}