package com.example.myapplication.ui.screens.hometab.components.dialogs

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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

  Column(
      modifier = Modifier.background(MaterialTheme.colors.background)
  ) {
    reminderTimings.forEach {
      if(showTimePicker){
        TimePicker(reminderTiming = it, setShowTimePicker = setShowTimePicker)
      }
      Row {
       Text(modifier = Modifier.weight(1f),
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