package com.example.myapplication.ui.screens.hometab.components.dialogs

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.data.models.ReminderTimings
import com.example.myapplication.ui.components.TimePicker
import com.example.myapplication.ui.theme.Typography

@Composable
fun ReminderTimingsDialog(setShowReminderTimingsDialog: (Boolean)->Unit){
  Dialog(
    onDismissRequest = { setShowReminderTimingsDialog(false) }
  ) {
    Box(
      modifier = Modifier
        .background(MaterialTheme.colors.background)
        .fillMaxHeight(0.87f)
    ) {
      ReminderTimingsDialogContent(setShowReminderTimingsDialog)
    }
  }
}

@Composable
fun ReminderTimingsDialogContent(setShowReminderTimingsDialog: (Boolean)->Unit){
  val (showTimePicker, setShowTimePicker) =  remember { mutableStateOf(false) }
  val reminderTimingsDialogViewModel = viewModel<ReminderTimingsDialogViewModel>()
  val selectedReminderTiming:MutableState<ReminderTimings?> =  remember { mutableStateOf(null) }
  val reminderTimings = reminderTimingsDialogViewModel.reminderTimings.collectAsState()
  val title = "Reminder Timings"
  
//  if(showTimePicker){
//    Log.d("$showTimePicker", "ReminderTimingsDialogContent: Show Time Picker")
//    TimePicker(
//      reminderTiming = selectedReminderTiming.component1(),
//      setShowTimePicker = setShowTimePicker,
//      reminderReminderTimingsDialogViewModel = reminderTimingsDialogViewModel
//    )
//  }

  Column {
    Title(title, setShowReminderTimingsDialog)

    ReminderTimingsList(
      reminderTimings = reminderTimings.value,
      reminderTimingsDialogViewModel = reminderTimingsDialogViewModel,
      modifier = Modifier.weight(1f,false),
      selectedReminderTiming = selectedReminderTiming,
      setShowTimePicker = setShowTimePicker
    )

    BottomBar(
      selectedReminderTiming = selectedReminderTiming,
      setShowTimePicker = setShowTimePicker
    )
  }
}


@Composable
fun Title(
  title:String,
  setShowReminderTimingsDialog: (Boolean)->Unit
){
  Row(
    modifier = Modifier.clickable {
      //Added because Dialog became unresponsive on clicking in unclickable areas
      setShowReminderTimingsDialog(false)
      setShowReminderTimingsDialog(true)
    }
  ){
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 0.dp, vertical = 8.dp),
      text = title,
      textAlign = TextAlign.Center
    )
    Divider(thickness = 4.dp)
  }
}

@Composable
fun ReminderTimingsList(
  reminderTimings: List<ReminderTimings>,
  reminderTimingsDialogViewModel:ReminderTimingsDialogViewModel,
  modifier:Modifier,
  selectedReminderTiming:MutableState<ReminderTimings?>,
  setShowTimePicker:(Boolean) -> Unit
){
  LazyColumn(
    modifier = modifier
  ){
    items(reminderTimings){
      Row {
        Text(modifier = Modifier
          .weight(1f)
          .clickable {
            selectedReminderTiming.value = it
            setShowTimePicker(true)
          },
          text = it.time,
          style = Typography.body1
        )
        Switch(
          checked = it.active,
          onCheckedChange = { currValue ->
            //Create a new instance of reminder timing for flow to recognize update in data
            val updatedReminderTiming = it.copy(active = currValue)
            reminderTimingsDialogViewModel.updateReminderTiming(updatedReminderTiming)
            //TODO("Delete Repeating notification for this time")
          }
        )
        IconButton(
          onClick = {
            reminderTimingsDialogViewModel.deleteReminderTiming(it)
            //TODO("Delete Repeating notification for this time")
          }
        ) {
          Icon(
            painter = painterResource(id = R.drawable.delete_icon),
            contentDescription = "Delete Reminder"
          )
        }
      }
    }
  }
}

@Composable
fun BottomBar(
  selectedReminderTiming:MutableState<ReminderTimings?>,
  setShowTimePicker:(Boolean) -> Unit
){
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(0.dp)
      .clickable {
        selectedReminderTiming.value = null
        setShowTimePicker(true)
      },
    horizontalArrangement = Arrangement.Center,
  ) {
    Icon(
      modifier = Modifier.padding(8.dp,16.dp),
      painter = painterResource(id = R.drawable.add_icon),
      contentDescription = "Add new Reminder"
    )
  }
}